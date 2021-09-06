package tqs.es.bookworms.Entities;

public class EnRouteClient implements OrderStatus {
    @Override
    public void performOperation(Order order, Operation operation) throws IllegalStateException {
        if(operation==Operation.CONFIRM_RECEPTION_CLIENT){
            //order.setStatus(new Fulfilled());
        }else if(operation==Operation.CHECK_STATUS){//check order status
            System.out.println(order.toString());
        }else{
            throw new IllegalStateException("Operation not supported when order is Waiting for Rider");
        }
    }
}
