package tqs.es.bookworms.Entities;

public class WaitingForRider implements OrderStatus{
    @Override
    public void performOperation(Order order, Operation operation) throws IllegalStateException {
        if(operation==Operation.DELIVER){
            //order.setStatus(new EnRouteClient());
            //System.out.println(order.toString() + " is on it's way to the customer");
        }else if(operation==Operation.CHECK_STATUS){//check order status
            System.out.println(order.toString());
        }else{
            throw new IllegalStateException("Operation not supported when order is Waiting for Rider");
        }
    }
}
