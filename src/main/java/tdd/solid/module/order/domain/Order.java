package tdd.solid.module.order.domain;

public class Order {
    private long orderId;
    private OrderState state;
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public OrderState getState() {
        return state;
    }
    public void setState(OrderState state) {
        this.state = state;
    }
    
    
}
