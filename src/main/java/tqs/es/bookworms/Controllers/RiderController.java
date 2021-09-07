package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.es.bookworms.DB.RiderRepository;
import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Entities.Order;
import tqs.es.bookworms.Entities.Rider;
import tqs.es.bookworms.Entities.RiderStatus;
import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

import java.util.List;

@Controller
@RequestMapping("/bookworms")
public class RiderController {

    @Autowired
    RiderRepository riderRepository;
    @Autowired
    OrderController orderController;

    //MainPage rider
    @GetMapping(value = "rider/{riderId}/orders")
    public String mainPageRider(@PathVariable("riderId") Long riderId, @PathVariable("orderId") Long orderId, Model model) throws ResourceNotFoundException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new ResourceNotFoundException("Rider Not found for id: " + riderId));;
        List<Order> ordersReadyToDeliver = orderController.findOrdersReadyToDeliverOnLocation(rider.getLocationId(), 2);
        model.addAttribute("rider", rider);
        model.addAttribute("ordersReadyToDeliver", ordersReadyToDeliver);
        return "rider/MainPage_form";
    }

    //entregar livros
    @PutMapping(value = "rider/{riderId}/orders/{orderId}")
    public String deliverOrder(@PathVariable("riderId") Long riderId, @PathVariable("orderId") Long orderId) throws ResourceNotFoundException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new ResourceNotFoundException("Rider Not found for id: " + riderId));;
        orderController.performOperation(orderId, Operation.DELIVER);
        rider.setStatus(RiderStatus.OCCUPIED);
        rider.deliverOrder(orderId);
        riderRepository.save(rider);
        String redirect = String.format("redirect:/bookworms/rider/%x", riderId);
        return redirect;
    }

    public String deliveryComplete(Long riderId) throws ResourceNotFoundException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new ResourceNotFoundException("Rider Not found for id: " + riderId));;
        rider.deliveryComplete();
        riderRepository.save(rider);
        //vai fazer redirect para uma página que o deixa à espera até que o cliente confirme a delivery
        return null;
    }
}
