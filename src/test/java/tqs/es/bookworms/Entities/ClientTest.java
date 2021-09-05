package tqs.es.bookworms.Entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientTest {


    private Location location1 = new Location();
    private Location location2 = new Location();
    private Client client1 = new Client("email", "Luís", location1.getId());
    private Client client2 = new Client("email", "Ana", location2.getId());

    @Mock
    private Book book1;
    @Mock
    private Book book2;
    @Mock
    private Book book3;
    /*
    private Book book1 = new Book("ABC de TQS", 2012, 15.99, location1.getId());
    private Book book2 = new Book("ABC de TQS", 2012, 15.99, location2.getId());
    private Book book3 = new Book("ABC de TQS", 2012, 15.99, location2.getId());

     */

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        Mockito.when( book1.getId()).thenReturn((long) 1);
        Mockito.when( book2.getId()).thenReturn((long) 2);
        Mockito.when( book3.getId()).thenReturn((long) 3);

        client2.addBooktoCart(book1.getId());
        client2.addBooktoCart(book2.getId());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @DisplayName("New clients start with an empty cart")
    @Test
    void cartEmptyOnCreation(){
        assertThat(client1.getCart().size(), is(0));
    }

    @DisplayName("Changing Clients location")
    @Test
    void setLocation() {
        client1.setLocationId(location2.getId());
        assertThat(client1.getLocationId(), is(location2.getId()));
    }

    @DisplayName("Adding books to cart updates it")
    @Test
    void addBookToCart() {
        client1.addBooktoCart(book1.getId());
        assertThat(client1.getCart().size(), is(1));
        assertTrue(client1.getCart().contains(book1.getId()));
        client1.addBooktoCart(book2.getId());
        assertThat(client1.getCart().size(), is(2));
        assertTrue(client1.getCart().contains(book2.getId()));

    }

    @DisplayName("Cart doesn't contain elements that haven't been added")
    @Test
    void searchForUnaddedBook(){
        assertFalse(client2.getCart().contains(book3.getId()));
    }

    @DisplayName("Adding book to cart updates it")
    @Test
    void addDuplicateToCart() {
        assertTrue(client1.addBooktoCart(book1.getId()));
        assertFalse(client1.addBooktoCart(book1.getId()));
    }

    @DisplayName("Removing book from cart")
    @Test
    void removeBookFromCart() {
        System.out.println(client2.getCart().size());
        assertTrue(client2.removeBookFromCart(book1.getId()));
        System.out.println(client2.getCart().size());
        assertThat(client2.getCart().size(), is(1));
        assertFalse(client2.getCart().contains(book1.getId()));
    }

    @DisplayName("Trying to remove a book that is not in the cart")
    @Test
    void removeBookNotInCart() {
        System.out.println(client2.getCart().size());
        assertFalse(client2.removeBookFromCart(book3.getId()));
    }

    @DisplayName("Emptying cart operation check")
    @Test
    void emptyCart() {
        client2.emptyCart();
        assertTrue(client2.getCart().isEmpty());
    }
}