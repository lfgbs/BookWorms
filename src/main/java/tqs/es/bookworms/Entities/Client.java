package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="client_table")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Indica que só deve haver uma ocorrência de cada email na base de dados. Assim podemos usá-lo para o login
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String address;
    @NotNull
    private Long locationId;
    @ElementCollection
    private List<String> cart = new ArrayList<>(); //cart guarda os títulos que o utilizador pretende comprar

    public Client(){}

    public Client(String email, String name, String address ,Long locationId) {
        this.email = email;
        this.name = name;
        this.address=address;
        this.locationId = locationId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public List<String> getCart() {
        return cart;
    }

    public void setCart(List<String> cart) {
        this.cart = cart;
    }

    public boolean addBooktoCart(String title){
        return this.cart.add(title);
    }

    public boolean removeBookFromCart(String title){
        return this.cart.remove(title);
    }

    //empties cart after sale
    public void emptyCart(){
        this.cart.clear();
    }

}
