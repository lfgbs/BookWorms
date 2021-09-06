package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_table")
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
    private Integer statusId;
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
        this.statusId = 0;
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
            this.setStatusId(2);
        }else{
            this.setStatusId(1);
        }
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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

    public void performOperation(Operation operation) throws IllegalStateException {
        switch (operation){
            case CHECK_STATUS:
                System.out.println(this.toString());
                break;
            case DELIVER:
                if(statusId==2){
                    setStatusId(3);
                    break;
                }

                throw new IllegalStateException("Operation only supported when order is Waiting for Rider");

            case CONFIRM_RECEPTION_CLIENT:
                if(statusId==3){
                    setStatusId(4);
                    break;
                }
                throw new IllegalStateException("Operation only supported when order is EnRouteClient");

            case CONFIRM_RECEPTION_LOCATION:
                if(statusId==1){
                    incrementBooksReady();
                    break;
                }
                throw new IllegalStateException("Operation only supported when order is EnRouteLocation");

        }




    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", locationId=" + locationId +
                ", buyerId=" + buyerId +
                ", bookIds=" + bookIds +
                ", status=" + statusId +
                ", riderId=" + riderId +
                ", placementDate=" + placementDate +
                '}';
    }


}
