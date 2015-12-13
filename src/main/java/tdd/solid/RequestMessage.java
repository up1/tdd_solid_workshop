package tdd.solid;

import java.util.List;

import tdd.solid.module.order.Operation;
import tdd.solid.module.order.domain.OrderItem;

public class RequestMessage {
    private long requestId;
    private Operation operation;
    private long orderId;
    private List<OrderItem> orderItems;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
