package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String location;

    public Location(){}

    public Location(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    /*

    public List<Long> getRiderIds() {
        return riderIds;
    }

    public void setRiderIds(List<Long> riderIds) {
        this.riderIds = riderIds;
    }


    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }



    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }


    public void addBook(Book book){}

    public void removeBook(Long bookId){}


    //returns Id of books if its found
    public Optional<Long> searchForBookInLocations(String title){
        return null;
    }

    //Called by another location when requesting a book, takes book out of this location's list
    public void transferBookOut(Long bookId){}

    //searches for book, takes it out from the original location, adds to this location
    public void transferBookIn(Long bookId){}



    //adds orderId to the order List
    public void receiveOrder(Long orderId){}

    //called when sending out an order to the client, removes the Id from the list
    public void sendOrder(){}



    //checking oldest order, oldest gets sent out first. Returns orderId
    public Long checkOldestOrder(){
        return null;
    }

    public void addRider(){}

    public void removerRider(){}
    */

}
