package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tqs.es.bookworms.DB.OrderRepository;
import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Entities.Order;
import tqs.es.bookworms.Entities.OrderStatus;

import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

import java.util.List;

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

    //SIGNALS TO THE RIDER THAT DELIVERY IS COMPLETE - CALLED IN CLIENTCONTROLLER
    public void deliveryComplete(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not found for id: " + orderId));;
        riderController.deliveryComplete(order.getRiderId());
    }

    //RETURNS ALL ORDERS ON A GIVEN LOCATION - CALLED IN LOCATIONCONTROLLER
    public List<Order> findOrdersOnLocation(Long locationId) {
        return null;
    }
}
