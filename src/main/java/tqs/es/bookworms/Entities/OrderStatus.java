package tqs.es.bookworms.Entities;

import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Entities.Order;

public interface OrderStatus {
    public void performOperation(Order order, Operation operation);
}
