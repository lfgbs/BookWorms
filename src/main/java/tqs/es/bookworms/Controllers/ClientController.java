package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import tqs.es.bookworms.DB.ClientRepository;
import tqs.es.bookworms.Entities.Client;
import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

@Controller
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OrderController orderController;

    @PutMapping(value="/client/{clientId}/orders/{orderId}")
    public String confirmReceptionClient(@PathVariable("clientId") Long clientId, @PathVariable("orderId") Long orderId) throws ResourceNotFoundException {
         Client client= clientRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Client Not found for id: " + clientId));
         client.confirmReception(orderId);
         orderController.performOperation(orderId, Operation.CONFIRM_RECEPTION_CLIENT);
         orderController.deliveryComplete(orderId);

         //redirects to orders page or maybe reloads this specific order page
        return null;
    }
}
