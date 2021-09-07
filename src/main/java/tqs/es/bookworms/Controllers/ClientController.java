package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.es.bookworms.DB.ClientRepository;
import tqs.es.bookworms.Entities.Book;
import tqs.es.bookworms.Entities.Client;
import tqs.es.bookworms.Entities.Operation;
import tqs.es.bookworms.Entities.Order;
import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

import java.util.List;

@RequestMapping(value = "/bookworms")
@Controller
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OrderController orderController;

    @Autowired
    BookController bookController;

    //represents the store, lists all titles with option to add to the cart
    @GetMapping(value = "/client/{clientId}")
    public String showAvailableBooks(@PathVariable("clientId") Long clientId, Model model){
        List<Book> booksAvailable = bookController.getAvailableBookTitles();
        model.addAttribute("clientId", clientId);
        model.addAttribute("booksAvailable", booksAvailable);
        return "client/MainPage_form";
    }

    //represents the cart, client sees all books in cart
    @GetMapping(value = "/client/{clientId}/cart")
    public String showCart(@PathVariable("clientId") Long clientId, Model model){
        Client client = clientRepository.getById(clientId);
        List<String> cart = client.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("client", client);
        return "client/Cart_form";
    }

    //Adding book to cart
    @PutMapping(value = "/client/{clientId}/{bookTitle}")
    public String addBookToCart(@PathVariable("clientId") Long clientId, @PathVariable("bookTitle") String bookTitle){
        Client client = clientRepository.getById(clientId);
        client.addBooktoCart(bookTitle);
        clientRepository.save(client);
        return "client/MainPage_form";
    }

    //page listing all orders
    @GetMapping(value = "/client/{clientId}/orders")
    public String allClientOrders(@PathVariable("clientId") Long clientId, Model model){
        List<Order> orders = orderController.getAllClientOrders(clientId);

        model.addAttribute("clientId",clientId);
        model.addAttribute("orders", orders);

        return "client/CheckAllOrders_Form";
    }

    //CONFIRM RECEPTION OF DELIVERY CHANGES ORDER STATUS TO DELIVERED AND RIDER STATUS TO FREE
    @PutMapping(value="/client/{clientId}/orders/{orderId}")
    public String confirmReceptionClient(@PathVariable("clientId") Long clientId, @PathVariable("orderId") Long orderId) throws ResourceNotFoundException {
         orderController.performOperation(orderId, Operation.CONFIRM_RECEPTION_CLIENT);
         orderController.deliveryComplete(orderId);

         String redirect = String.format("redirect:/bookworms/client/%x/orders", clientId);
         return redirect;
    }
}
