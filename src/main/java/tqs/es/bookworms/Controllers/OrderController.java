package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tqs.es.bookworms.DB.OrderRepository;
import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Entities.Order;
import tqs.es.bookworms.Entities.OrderStatus;

import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RiderController riderController;

    public OrderStatus getStatus(Long id) {
        return null;
    }

    public void performOperation(Long orderId, Operation operation) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not found for id: " + orderId));;
        order.performOperation(operation);
    }

    public void deliveryComplete(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not found for id: " + orderId));;
        riderController.deliveryComplete(order.getRiderId());
    }
}
