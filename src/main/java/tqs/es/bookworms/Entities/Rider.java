package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;
    private Long locationId;
    @NotNull
    private RiderStatus status;
    private Long orderId;

    public Rider(){}

    public Rider(String email, String name) {
        this.email = email;
        this.name = name;
        this.status = RiderStatus.AVAILABLE;
    }

    public Rider(String email, String name, Long locationId) {
        this.email = email;
        this.name = name;
        this.locationId = locationId;
        this.status = RiderStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long location) {
        this.locationId = location;
    }

    public RiderStatus getStatus() {
        return status;
    }

    public void setStatus(RiderStatus status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    //changes orderStatus to deliver
    public void deliverOrder(Long orderId){
        setStatus(RiderStatus.OCCUPIED);
        setOrderId(orderId);
    }

    public void deliveryComplete() {
        setStatus(RiderStatus.AVAILABLE);
        setOrderId(null);
    }

    @Override
    public String toString() {
        return "Rider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + locationId + '\'' +
                '}';
    }


}
