package tdd.solid.module.order;

import tdd.solid.ResponseMessage;
import tdd.solid.module.order.domain.Order;

public class OrderDetailResponseMessage extends ResponseMessage {

    private Order order;

    public OrderDetailResponseMessage(long requestId, Order order) {
        super(requestId);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
