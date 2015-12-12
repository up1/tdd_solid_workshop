package tdd.solid;

public class ResponseMessage {
    private long requestId;

    public ResponseMessage(long requestId) {
        this.requestId = requestId;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
    
}
