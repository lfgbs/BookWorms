package tqs.es.bookworms.Controllers;

import org.springframework.stereotype.Controller;
import tqs.es.bookworms.Entities.Book;

import java.util.List;

@Controller
public class BookController {

    //RETURNS ALL BOOKS WITH STATUS==ENROUTELOCATION AND LOCATIONID==LOCATIONID - CALLED IN LOCATIONCONTROLLER
    public List<Book> findBooksEnRouteLocation(Long locationId) {
        return null;
    }

    //RETURNS ALL BOOKS WITH STATUS==AVAILABLE AND LOCATIONID==LOCATIONID - CALLED IN LOCATIONCONTROLLER
    public List<Book> findBooksOnLocation(Long locationId) {
        return null;
    }

    //RECEIVES A BOOK, ADDS LOCATIONID == LOCATIONID AND CREATES THE BOOK - CALLED IN LOCATIONCONTROLLER
    public void addBookToLocation(Book book, Long locationId) {
    }

    //CHANGES STATUS TO RESERVED - CALLED IN LOCATIONCONTROLLER
    public void confirmReceptionLocation(Long bookId) {
    }
}
