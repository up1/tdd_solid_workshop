package tdd.solid;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import tdd.solid.module.order.OrderProcessor;

@Path("/")
public class MessageProcessor {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage processMessage(RequestMessage requestMessage) {
        return new OrderProcessor().processMessage(requestMessage);
    }
    
}
