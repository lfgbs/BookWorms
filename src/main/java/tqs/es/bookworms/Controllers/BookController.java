package tqs.es.bookworms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tqs.es.bookworms.DB.BookRepository;
import tqs.es.bookworms.Entities.Book;
import tqs.es.bookworms.Entities.BookStatus;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    //RETURNS ALL BOOKS WITH STATUS==ENROUTELOCATION AND LOCATIONID==LOCATIONID - CALLED IN LOCATIONCONTROLLER
    public List<Book> findBooksEnRouteLocation(Long locationId) {
        return bookRepository.findBooksByStatusAndLocationId(BookStatus.EN_ROUTE_LOCATION, locationId);
    }

    //RETURNS ALL BOOKS WITH STATUS==AVAILABLE AND LOCATIONID==LOCATIONID - CALLED IN LOCATIONCONTROLLER
    public List<Book> findBooksOnLocation(Long locationId) {
        return bookRepository.findBooksByStatusAndLocationId(BookStatus.AVAILABLE, locationId);
    }

    //RECEIVES A BOOK, ADDS LOCATIONID == LOCATIONID AND CREATES THE BOOK - CALLED IN LOCATIONCONTROLLER
    public void addBookToLocation(Book book, Long locationId) {
        book.setLocationId(locationId);
        bookRepository.save(book);
    }

    //CHANGES STATUS TO RESERVED - CALLED IN LOCATIONCONTROLLER
    public void confirmReceptionLocation(Long bookId) {
        Book book = bookRepository.getById(bookId);
        book.setStatus(BookStatus.RESERVED);
        bookRepository.save(book);
    }

    //Returns unique books status == Available - called in clientController
    public List<Book> getAvailableBookTitles() {
        return bookRepository.findDistinctTitleByStatus(BookStatus.AVAILABLE);
    }

    //
    public List<Book> retrieveBooksFromTitles(String title, int quantity) {
        return bookRepository.findTop10ByTitle(title);
    }

    //Save Book or changes to book
    public void saveBook(Book book) {
        bookRepository.save(book);
    }


    public boolean checkAvailableTitleQuantities(String title, Integer quantity) {
        return (quantity<=bookRepository.countBooksByTitleAndStatus(title, BookStatus.AVAILABLE));
    }
}
