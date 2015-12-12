package tdd.solid.module.order;

import java.io.File;
import java.util.Map;

import tdd.solid.RequestMessage;
import tdd.solid.ResponseMessage;
import tdd.solid.module.order.domain.Order;

import com.thoughtworks.xstream.XStream;

public class OrderProcessor {
    
    private static final String ORDER_FILE_NAME = "data/orders.xml";
    private Map<Long, Order> orders;
    
    public OrderProcessor() {
        readOrderFromFile();
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
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private void readOrderFromFile() {
        orders = (Map<Long, Order>) new XStream().fromXML(new File(ORDER_FILE_NAME));
    }

}
