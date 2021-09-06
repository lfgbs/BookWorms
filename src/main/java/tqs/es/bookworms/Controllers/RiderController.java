package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.es.bookworms.DB.RiderRepository;
import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Entities.Rider;
import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

@Controller
@RequestMapping("/bookworms")
public class RiderController {

    @Autowired
    RiderRepository riderRepository;
    @Autowired
    OrderController orderController;

    @PutMapping(value = "rider/{riderId}/location/orders/{orderId}")
    public String deliverOrder(@PathVariable("riderId") Long riderId, @PathVariable("orderId") Long orderId) throws ResourceNotFoundException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new ResourceNotFoundException("Rider Not found for id: " + riderId));;
        orderController.performOperation(orderId, Operation.DELIVER);
        rider.deliverOrder(orderId);
        //vai fazer redirect para uma página que o deixa à espera até que o cliente confirme a delivery
        return null;
    }

    public String deliveryComplete(Long riderId) throws ResourceNotFoundException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new ResourceNotFoundException("Rider Not found for id: " + riderId));;
        rider.deliveryComplete();
        //vai fazer redirect para uma página que o deixa à espera até que o cliente confirme a delivery
        return null;
    }
}
