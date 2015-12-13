package tdd.solid.module.order;

import tdd.solid.ResponseMessage;

public class OrderSubmissionResponseMessage extends ResponseMessage {

    private long orderId;
    private String orderState;

    public OrderSubmissionResponseMessage(long requestId, long orderId, String orderState) {
        super(requestId);
        this.orderId = orderId;
        this.orderState = orderState;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

}
