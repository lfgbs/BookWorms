package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Long> bookIds = new ArrayList<>();
    @NotNull
    private int booksReady;
    @NotNull
    private OrderStatus status;
    private Rider rider;

    public Order() {}

    public Order(Long locationId, Long buyerId, List<Long> bookIds, int booksReady ,OrderStatus status, Rider rider) {
        this.locationId=locationId;
        this.buyerId = buyerId;
        this.bookIds = bookIds;
        this.booksReady = booksReady;
        this.status = status;
        this.rider=rider;
    }

    public Long getLocationId() {
        return locationId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public List<Long> getBookIds() {
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

    private void checkForBooksReady(){

    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public void performAction(Operation operation){status.performOperation(this, operation);}

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", buyerId=" + buyerId +
                ", bookIds=" + bookIds +
                ", status=" + status +
                ", rider=" + rider +
                '}';
    }
}
