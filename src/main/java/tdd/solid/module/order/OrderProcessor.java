package tdd.solid.module.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import tdd.solid.RequestMessage;
import tdd.solid.ResponseMessage;
import tdd.solid.module.order.domain.InventoryItem;
import tdd.solid.module.order.domain.Order;
import tdd.solid.module.order.domain.OrderItem;
import tdd.solid.module.order.domain.OrderItemState;
import tdd.solid.module.order.domain.OrderState;

import com.thoughtworks.xstream.XStream;

public class OrderProcessor {

    private static final String ORDER_FILE_NAME = "data/orders.xml";
    private static final String INVENTORY_FILE_NAME = "data/inventory.xml";
    
    private Map<Long, Order> orders;
    private Map<String, InventoryItem> inventory;
    private long lastOrderId = 0;

    public OrderProcessor() {
        readInventoryFromFile();
        readOrderFromFile();
        lastOrderId = orders.values()
                .stream()
                .mapToLong(Order::getOrderId)
                .max()
                .orElse(0);
    }

    public ResponseMessage processMessage(RequestMessage requestMessage) {
        ResponseMessage responseMessage = null;

        switch (requestMessage.getOperation()) {
        case SUBMIT_ORDER:
            responseMessage = createOrder(requestMessage);
            break;
        case CANCEL_ORDER:
            responseMessage = cancelOrder(requestMessage);
            break;
        case GET_ORDER_DETAIL:
            responseMessage = getOrder(requestMessage);
            break;
        }

        return responseMessage;
    }

    private ResponseMessage getOrder(RequestMessage requestMessage) {
        Order order = orders.get(requestMessage.getOrderId());
        return createOrderDetailResponseMessage(requestMessage, order);
    }

    private ResponseMessage createOrderDetailResponseMessage(RequestMessage requestMessage, Order order) {
        return new OrderDetailResponseMessage(requestMessage.getRequestId(), order);
    }

    private ResponseMessage cancelOrder(RequestMessage requestMessage) {
        return null;
    }

    private ResponseMessage createOrder(RequestMessage requestMessage) {
        Order newOrder = new Order();
        newOrder.setOrderId(++lastOrderId);
        newOrder.getOrderItems().addAll(requestMessage.getOrderItems());

        newOrder.getOrderItems().forEach(currentItem -> {
            
            //Check quantity from inventory
            InventoryItem inventoryItem = inventory.get(currentItem.getItemCode());
            if(inventoryItem.getQuantity() >= currentItem.getQuantity()) {
                inventoryItem.setQuantity(inventoryItem.getQuantity() - currentItem.getQuantity());
                
                //Calculate price
                calculatePrice(currentItem, inventoryItem);
                
                currentItem.setState(OrderItemState.FILLED);
            } else {
                currentItem.setState(OrderItemState.NOT_ENOUGH_QUANTITY);
            }
        });

        newOrder.setState(newOrder.getOrderItems().stream().allMatch(o -> o.getState() == OrderItemState.FILLED) 
                ? OrderState.FILLED
                : OrderState.PROCESSING);
        
        orders.put(newOrder.getOrderId(), newOrder);
        
        // Save inventory to file
        writeInventoryToFile();

        // Save order to file
        writeOrderToFile();

        return createOrderResponseMessage(requestMessage, newOrder);
    }

    private void calculatePrice(OrderItem currentItem, InventoryItem inventoryItem) {
        
    }

    private ResponseMessage createOrderResponseMessage(RequestMessage requestMessage, Order newOrder) {
        return new OrderSubmissionResponseMessage(requestMessage.getRequestId(), newOrder.getOrderId(), newOrder.getState().toString());
    }

    @SuppressWarnings("unchecked")
    private void readInventoryFromFile() {
        inventory = (Map<String, InventoryItem>) new XStream().fromXML(new File(INVENTORY_FILE_NAME));
    }
    
    private void writeInventoryToFile() {
        try (OutputStream stream = new FileOutputStream(INVENTORY_FILE_NAME)) {
            new XStream().toXML(inventory, stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write inventory: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void readOrderFromFile() {
        orders = (Map<Long, Order>) new XStream().fromXML(new File(ORDER_FILE_NAME));
    }

    private void writeOrderToFile() {
        try (OutputStream stream = new FileOutputStream(ORDER_FILE_NAME)) {
            new XStream().toXML(orders, stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write orders: " + e.getMessage());
        }
    }

}
