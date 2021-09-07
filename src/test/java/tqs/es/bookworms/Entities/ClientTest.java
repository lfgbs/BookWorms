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
    private Client client1 = new Client("email", "Luís", "Rua" ,location1.getId());
    private Client client2 = new Client("email", "Ana", "Travessa" ,location2.getId());

    @Mock
    private Book book1;
    @Mock
    private Book book2;
    @Mock
    private Book book3;
    @Mock
    private Book book4;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        Mockito.when( book1.getId()).thenReturn((long) 1);
        Mockito.when( book2.getId()).thenReturn((long) 2);
        Mockito.when( book3.getId()).thenReturn((long) 3);
        Mockito.when( book4.getId()).thenReturn((long) 4);
        Mockito.when( book1.getTitle()).thenReturn("title1");
        Mockito.when( book2.getTitle()).thenReturn("same book");
        Mockito.when( book3.getTitle()).thenReturn("same book");
        Mockito.when( book4.getTitle()).thenReturn("another book");

        client2.addBooktoCart(book1.getTitle());
        client2.addBooktoCart(book2.getTitle());
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
        client1.addBooktoCart(book1.getTitle());
        assertThat(client1.getCart().size(), is(1));
        assertTrue(client1.getCart().contains(book1.getTitle()));
        client1.addBooktoCart(book2.getTitle());
        assertThat(client1.getCart().size(), is(2));
        assertTrue(client1.getCart().contains(book2.getTitle()));
        client1.addBooktoCart(book3.getTitle());
        assertThat(client1.getCart().size(), is(3));

    }

    @DisplayName("Cart doesn't contain elements that haven't been added")
    @Test
    void searchForUnaddedBook(){
        assertFalse(client2.getCart().contains(book4.getTitle()));
    }

    @DisplayName("Removing book from cart")
    @Test
    void removeBookFromCart() {
        assertTrue(client2.removeBookFromCart(book1.getTitle()));
        assertThat(client2.getCart().size(), is(1));
        assertFalse(client2.getCart().contains(book1.getTitle()));
    }

    @DisplayName("Trying to remove a book that is not in the cart")
    @Test
    void removeBookNotInCart() {
        assertFalse(client2.removeBookFromCart(book4.getTitle()));
    }

    @DisplayName("Emptying cart operation check")
    @Test
    void emptyCart() {
        client2.emptyCart();
        assertTrue(client2.getCart().isEmpty());
    }
}