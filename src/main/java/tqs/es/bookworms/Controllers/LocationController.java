package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.es.bookworms.DB.LocationRepository;
import tqs.es.bookworms.Entities.Book;
import tqs.es.bookworms.Entities.Location;
import tqs.es.bookworms.Entities.Order;
import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

import java.util.List;

@RequestMapping(value = "/bookworms/manager/{managerId}")
@Controller
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    OrderController orderController;

    @Autowired
    BookController bookController;

    //Manager CLICKING BUTTON THAT TAKES TO Location
    @GetMapping(value = "/location/{locationId}")
    public String locationMainPage(@PathVariable("managerId") Long managerId, @PathVariable("locationId") Long locationId, Model model){
        Location location = locationRepository.getById(locationId);
        List<Book> booksEnRouteLocation = bookController.findBooksEnRouteLocation(locationId);
        List<Book> booksOnLocation = bookController.findBooksOnLocation(locationId);
        List<Order> ordersOnLocation = orderController.findOrdersOnLocation(locationId);
        model.addAttribute("location", location);
        model.addAttribute("booksEnRouteLocation", booksEnRouteLocation);
        model.addAttribute("booksOnLocation", booksOnLocation);
        model.addAttribute("ordersOnLocation", ordersOnLocation);
        return "location/MainPage_form";
    }

    //CLICKING BUTTON THAT TAKES TO FORM TO ADD Books To LOCATION (Books are ADDED IN BookCONTROLLER-redirect)
    @GetMapping(value = "/location/{locationId}/addBooksForm")
    public String addBooksToLocationForm(@PathVariable("managerId") Long managerId, @PathVariable("locationId") Long locationId, Model model){

        model.addAttribute("locationId", locationId);
        model.addAttribute("book", new Book());
        return "location/AddBooksForm_form";
    }

    //ADDING THE BOOKS
    @PostMapping(value = "/location/{locationId}/addBooks/form")
    public String addBooksToLocation(@PathVariable("managerId") Long managerId, @PathVariable("locationId") Long locationId, @ModelAttribute Book book, @ModelAttribute int quantity){
        for(int i=0; i<quantity; i++){
            bookController.addBookToLocation(book, locationId);
        }

        String redirect = String.format("redirect:/bookworms/manager/%x/location/%x", managerId, locationId);
        return redirect;
    }

    //CONFIRMING RECEPTION
    @PutMapping(value = "/manager/{managerId}/location/{locationId}/{bookId}")
    public String confirmReceptionLocation(@PathVariable("managerId") Long managerId, @PathVariable("locationId") Long locationId, @PathVariable("bookId") Long bookId){
        bookController.confirmReceptionLocation(bookId);
        String redirect = String.format("redirect:/bookworms/manager/%x/location/%x", managerId, locationId);
        return redirect;
    }

    //Gets All Locations - called by managerController
    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    //CREATES THE LOCATION RECEIVED - CALLED IN MANAGERCONTROLLER
    public void addLocation(Location location) throws ResourceNotFoundException {
        //Save location if it is up to standard and unique in the db
        try{
            locationRepository.save(location);
        }catch (Exception e){
            throw new ResourceNotFoundException("Couldn't save location");
        }
    }

    /////////////////////////////////////////
    //ADDING THE BOOKS
    @PostMapping(value = "/location")
    public void addLocationPostman(@PathVariable("managerId") Long managerId){
        locationRepository.save(new Location("Lisboa"));
    }
}
