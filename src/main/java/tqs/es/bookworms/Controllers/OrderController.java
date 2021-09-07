package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tqs.es.bookworms.DB.OrderRepository;
import tqs.es.bookworms.Entities.*;

import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

import java.util.*;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookController bookController;

    @Autowired
    RiderController riderController;

    //placing order
    @PostMapping(value = "/client/{clientId}/cart")
    public String placeOrder(@PathVariable("clientId") Long clientId, @ModelAttribute List<String> cart, @ModelAttribute Client client){

        //////////////////
       // verificar se podemos satisfazer a reserva // TODO
        //////////////////
        Map<String, Integer> bookQuantities =   new HashMap<>() ;
        List<Book> books = new ArrayList<>();
        Set<Long> bookIds = new HashSet<>();
        int booksReady = 0;

        //checking if there is enough books in stores
        //counting ocurrences of each unique title
        for(String title:cart){
            if(bookQuantities.containsKey(title)){
                bookQuantities.put(title, bookQuantities.get(title)+1);
            }else{
                bookQuantities.put(title, 1);
            }
        }

        //checking quantities in stores
        if(checkAvailableTitleQuantities(bookQuantities)){
            //changing status according to bookLocation and orderLocation
            for(String title:bookQuantities.keySet()){
                List<Book> booksRetrievedBytitle = bookController.retrieveBooksFromTitles(title, bookQuantities.get(title));
                for(Book book:booksRetrievedBytitle){
                    if(book.getLocationId()==client.getLocationId()){
                        book.setStatus(BookStatus.AVAILABLE);
                        booksReady++;
                    }else{
                        book.setLocationId(client.getLocationId());
                        book.setStatus(BookStatus.EN_ROUTE_LOCATION);
                    }
                    books.add(book);
                }
            }

            //saving changes to the books status
            for(Book book:books){
                bookIds.add(book.getId());
                bookController.saveBook(book);
            }

            Order order = new Order(client.getLocationId(), clientId, client.getAddress(), bookIds, booksReady);
            orderRepository.save(order);

        }else{//se não houver livros suficientes, cria a order com status 5(not able to fulfil)
            Order order = new Order(client.getLocationId(), clientId, client.getAddress(), 5);
            orderRepository.save(order);
        }

        String redirect = String.format("redirect:/bookworms/client/%x/orders", clientId);
        return redirect;
    }

    private boolean checkAvailableTitleQuantities(Map<String, Integer> bookQuantities) {
        boolean enoughBooks=true;
        for(String title:bookQuantities.keySet()){
            if(!enoughBooks){
                return enoughBooks;
            }
            enoughBooks=bookController.checkAvailableTitleQuantities(title, bookQuantities.get(title));
        }
        return enoughBooks;
    }


    public void performOperation(Long orderId, Operation operation) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not found for id: " + orderId));;
        order = order.performOperation(operation);
        orderRepository.save(order);
    }

    //SIGNALS TO THE RIDER THAT DELIVERY IS COMPLETE - CALLED IN CLIENTCONTROLLER
    public void deliveryComplete(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not found for id: " + orderId));;
        order.setStatusId(4);
        riderController.deliveryComplete(order.getRiderId());
    }

    //RETURNS ALL ORDERS ON A GIVEN LOCATION - CALLED IN LOCATIONCONTROLLER
    public List<Order> findOrdersOnLocation(Long locationId) {
        return orderRepository.findOrdersByLocationId(locationId);
    }

    //returns all orders with clientId==clientId - called in clientController
    public List<Order> getAllClientOrders(Long clientId) {
        return orderRepository.findOrdersByBuyerId(clientId);
    }

    //returns all readyToDeliver Orders in a location
    public List<Order> findOrdersReadyToDeliverOnLocation(Long locationId, int statusId){
        return orderRepository.findOrdersByLocationIdAndStatusId(locationId, statusId);
    }
}
