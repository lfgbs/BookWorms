package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String address;
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

    public Order(Long locationId, Long buyerId, String address,Set<Long> bookIds, int booksReady) {
        this.locationId=locationId;
        this.buyerId = buyerId;
        this.address=address;
        this.bookIds = bookIds;
        this.booksReady = booksReady;
        this.status = new Placing();
        long milliSeconds = System.currentTimeMillis();
        this.placementDate= new Date(milliSeconds);
        checkOrderReady();
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
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

    public int getBooksReady() {
        return booksReady;
    }

    public void setBooksReady(int booksReady) {
        this.booksReady = booksReady;
    }

    public void checkOrderReady(){
        if (this.booksReady == this.bookIds.size()){
            this.setStatus(new WaitingForRider());
        }else{
            this.setStatus(new EnRouteLocation());
        }
    }

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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void incrementBooksReady(){
        this.booksReady++;
        checkOrderReady();
    }

    public void performAction(Operation operation){status.performOperation(this, operation);}

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", locationId=" + locationId +
                ", buyerId=" + buyerId +
                ", bookIds=" + bookIds +
                ", status=" + status +
                ", riderId=" + riderId +
                ", placementDate=" + placementDate +
                '}';
    }
}
