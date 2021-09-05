package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long locationId;
    @NotNull
    private Long buyerId;
    @ElementCollection //lista de livros na encomenda
    private Set<Long> bookIds = new HashSet<>();
    @NotNull
    private int booksReady;
    @NotNull
    private OrderStatus status;
    private Long riderId;
    @Temporal(TemporalType.DATE)
    Date placementDate;

    public Order() {}

    public Order(Long locationId, Long buyerId, Set<Long> bookIds, int booksReady ,OrderStatus status, Long riderId) {
        this.locationId=locationId;
        this.buyerId = buyerId;
        this.bookIds = bookIds;
        this.booksReady = booksReady;
        this.status = status;
        this.riderId=riderId;
        long milliSeconds = System.currentTimeMillis();
        this.placementDate= new Date(milliSeconds);
    }

    public Long getLocationId() {
        return locationId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Set<Long> getBookIds() {
        return bookIds;
    }

    public int getBookListSize() {
        return bookIds.size();
    }

    public int getBooksReady() {
        return booksReady;
    }

    public void setBooksReady(int booksReady) {
        this.booksReady = booksReady;
    }

    private void checkForBooksReady(){}

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getRiderId() {
        return riderId;
    }

    public void setRider(Long riderId) {
        this.riderId = riderId;
    }

    public void performAction(Operation operation){status.performOperation(this, operation);}

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", buyerId=" + buyerId +
                ", bookIds=" + bookIds +
                ", status=" + status +
                ", rider=" + riderId +
                '}';
    }
}
