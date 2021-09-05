package tqs.es.bookworms.Entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderTest {

    @Mock
    private Location location1;
    @Mock
    private Client client1;
    @Mock
    private Rider rider1;
    @Mock
    private Book book1;
    @Mock
    private Book book2;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        Mockito.when( location1.getId()).thenReturn((long) 1);
        Mockito.when( client1.getId()).thenReturn((long) 1);
        Mockito.when(client1.getAddress()).thenReturn("Rua");
        Mockito.when( rider1.getId()).thenReturn((long) 1);
        Mockito.when( book1.getId()).thenReturn((long) 1);
        Mockito.when( book2.getId()).thenReturn((long) 2);

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @DisplayName("Checking location on creation")
    @Test
    void getLocation(){
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        assertThat(order1.getLocationId(),is((long)1));
    }

    @DisplayName("On creation status is EnRouteLocation if not all books are ready")
    @Test
    void checkStatusOnCreationNotAllBooksReady(){
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        assertTrue(order1.getStatus().getClass().equals(new EnRouteLocation().getClass()));
        assertFalse(order1.getStatus().getClass().equals(new WaitingForRider().getClass()));
    }

    @DisplayName("On creation status is WaitingForRider if all books are ready ")
    @Test
    void checkStatusOnCreationAllBooksReady(){
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 2 );
        assertTrue(order1.getStatus().getClass().equals(new WaitingForRider().getClass()));
    }

    @DisplayName("On creation Rider is null")
    @Test
    void checkRiderOnCreation(){
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        assertTrue(order1.getRiderId()==null);
    }

    @DisplayName("Checking BooksReady on creation")
    @Test
    void booksReadyOnCreation(){
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 0 );

        assertThat(order1.getBooksReady(), is(1));
        assertThat(order2.getBooksReady(), is(0));
    }

    @DisplayName("Incrementing books ready")
    @Test
    void incrementBooksReady() {
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        order1.incrementBooksReady();
        assertThat(order1.getBooksReady(), is(2));
    }

    @DisplayName("Confirming book reception increments booksReady")
    @Test
    void confirmingBookReception() {
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        order1.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        assertThat(order1.getBooksReady(), is(2));
    }

    @DisplayName("Status updates to WaitingForRider when all books ready")
    @Test
    void incrementBooksReadyCallsCheckOrderReady() {
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        order1.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        assertTrue(order1.getStatus().getClass().equals(new WaitingForRider().getClass()));

    }

    @Test
    void setRider(){
        Order order1 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        order1.setRider(rider1.getId());
        assertThat(order1.getRiderId(), is((long)1));

    }

    @DisplayName("Delivering Order when order is ready")
    @Test
    void performDeliverOnWaitingForRider() {
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 2 );
        order2.performOperation(Operation.DELIVER);
        assertTrue(order2.getStatus().getClass().equals(new EnRouteClient().getClass()));
    }

    @DisplayName("Delivering Order when order is not ready")
    @Test
    void performDeliverOnInvalidStatus() {
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        assertThrows(IllegalStateException.class, ()->order2.performOperation(Operation.DELIVER));
        assertFalse(order2.getStatus().getClass().equals(new EnRouteClient().getClass()));
    }

    @DisplayName("Confirming reception at location when order is EnRouteLocation")
    @Test
    void performConfirmReceptionLocationOnEnRouteLocation() {
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 0 );
        assertTrue(order2.getStatus().getClass().equals(new EnRouteLocation().getClass()));
        order2.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        //confirming status remains the same because not all books have arrived
        assertFalse(order2.getStatus().getClass().equals(new EnRouteClient().getClass()));
    }

    @DisplayName("Confirming reception at location when order is not EnRouteLocation")
    @Test
    void performConfirmReceptionLocationOnInvalidStatus() {
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 2 );
        assertThrows(IllegalStateException.class, ()->order2.performOperation(Operation.CONFIRM_RECEPTION_LOCATION));
        //Confirming that booksReady hasn't been incremented
        assertThat(order2.getBooksReady(), is(2));
    }

    @DisplayName("Confirming reception at location when order is EnRouteLocation")
    @Test
    void performConfirmReceptionClientOnEnRouteClient() {
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 2 );
        order2.performOperation(Operation.DELIVER);
        order2.performOperation(Operation.CONFIRM_RECEPTION_CLIENT);
        assertTrue(order2.getStatus().getClass().equals(new Fulfilled().getClass()));
    }

    @DisplayName("Confirming reception at location when order is not EnRouteLocation")
    @Test
    void performConfirmReceptionClientOnInvalidStatus() {
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );
        //Checking For Status == EnRouteLocation
        assertThrows(IllegalStateException.class, ()->order2.performOperation(Operation.CONFIRM_RECEPTION_CLIENT));
        //Checking for status == WaitingFOrRIder
        order2.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        assertThrows(IllegalStateException.class, ()->order2.performOperation(Operation.CONFIRM_RECEPTION_CLIENT));
        assertFalse(order2.getStatus().getClass().equals(new Fulfilled().getClass()));

    }

    @DisplayName("Checking status is possible in any orderStatus")
    @Test
    void performingStatusCheckOnEveryOrderStatus(){
        Order order2 = new Order(location1.getId(), client1.getId(), client1.getAddress() ,new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())) , 1 );

        order2.performOperation(Operation.CHECK_STATUS);
        assertTrue(order2.getStatus().getClass().equals(new EnRouteLocation().getClass()));

        order2.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        order2.performOperation(Operation.CHECK_STATUS);
        assertTrue(order2.getStatus().getClass().equals(new WaitingForRider().getClass()));

        order2.performOperation(Operation.DELIVER);
        order2.performOperation(Operation.CHECK_STATUS);
        assertTrue(order2.getStatus().getClass().equals(new EnRouteClient().getClass()));

        order2.performOperation(Operation.CONFIRM_RECEPTION_CLIENT);
        order2.performOperation(Operation.CHECK_STATUS);
        assertTrue(order2.getStatus().getClass().equals(new Fulfilled().getClass()));

    }

}