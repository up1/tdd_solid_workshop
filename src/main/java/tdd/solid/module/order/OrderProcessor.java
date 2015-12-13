package tdd.solid.module.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import tdd.solid.RequestMessage;
import tdd.solid.ResponseMessage;
import tdd.solid.module.order.domain.Order;
import tdd.solid.module.order.domain.OrderState;

import com.thoughtworks.xstream.XStream;

public class OrderProcessor {
    
    private static final String ORDER_FILE_NAME = "data/orders.xml";
    private Map<Long, Order> orders;
    private long lastOrderId = 0;
    
    public OrderProcessor() {
        readOrderFromFile();
        lastOrderId = orders.values()
                .stream()
                .mapToLong(Order::getOrderId)
                .max()
                .orElse(0);
    }

    public ResponseMessage processMessage(RequestMessage requestMessage) {
        ResponseMessage responseMessage = null;
        
        switch( requestMessage.getOperation() ) {
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
        newOrder.setState(OrderState.PROCESSING);
        
        orders.put(newOrder.getOrderId(), newOrder);
        
        //Save order to file
        writeOrderToFile();
        
        return createOrderResponseMessage(requestMessage, newOrder);
    }

    private ResponseMessage createOrderResponseMessage(RequestMessage requestMessage, Order newOrder) {
        return new OrderSubmissionResponseMessage(requestMessage.getRequestId(), newOrder.getOrderId(), newOrder.getState().toString());
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
