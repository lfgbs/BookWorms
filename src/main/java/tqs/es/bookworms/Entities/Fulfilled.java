package tqs.es.bookworms.Entities;

public class Fulfilled implements OrderStatus{
    @Override
    public void performOperation(Order order, Operation operation) throws IllegalStateException {
        if(operation==Operation.CHECK_STATUS){
            System.out.println(order.toString());
        }else{
            throw new IllegalStateException("Operation not supported when order is Waiting for Rider");
        }
    }
}
