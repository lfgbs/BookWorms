package tqs.es.bookworms.Entities;

import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Entities.Order;
import tqs.es.bookworms.Entities.OrderStatus;

public class EnRouteLocation implements OrderStatus {

    @Override
    public void performOperation(Order order, Operation operation) throws IllegalStateException{
        if(operation==Operation.CONFIRM_RECEPTION_LOCATION){
            order.incrementBooksReady();
        }else if(operation==Operation.CHECK_STATUS){//check order status
            System.out.println(order.toString());
        }else{
            throw new IllegalStateException("Operation not supported when order is EnRouteLocation");
        }
    }
}
