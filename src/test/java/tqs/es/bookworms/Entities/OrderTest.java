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

    Order order1 = new Order();
    Order order2 = new Order();
    Order order3 = new Order();


    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        Mockito.when( location1.getId()).thenReturn((long) 1);
        Mockito.when( client1.getId()).thenReturn((long) 1);
        Mockito.when(client1.getAddress()).thenReturn("Rua");
        Mockito.when( rider1.getId()).thenReturn((long) 1);
        Mockito.when( book1.getId()).thenReturn((long) 1);
        Mockito.when( book2.getId()).thenReturn((long) 2);

        order1.setLocationId(location1.getId());
        order1.setBuyerId(client1.getId());
        order1.setAddress(client1.getAddress());
        order1.setBookIds(new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())));
        order1.setBooksReady(1);

        order2.setLocationId(location1.getId());
        order2.setBuyerId(client1.getId());
        order2.setAddress(client1.getAddress());
        order2.setBookIds(new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())));
        order2.setBooksReady(0);

        order3.setLocationId(location1.getId());
        order3.setBuyerId(client1.getId());
        order3.setAddress(client1.getAddress());
        order3.setBookIds(new HashSet<Long>(Arrays.asList(book1.getId(), book2.getId())));
        order3.setBooksReady(2);

        order1.checkOrderReady();
        order2.checkOrderReady();
        order3.checkOrderReady();
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
        assertThat(order1.getStatusId(), is(1));
    }

    @DisplayName("On creation status is WaitingForRider if all books are ready ")
    @Test
    void checkStatusOnCreationAllBooksReady(){
        assertThat(order3.getStatusId(), is(2));

    }

    @DisplayName("On creation Rider is null")
    @Test
    void checkRiderOnCreation(){
        assertTrue(order1.getRiderId()==null);
    }

    @DisplayName("Checking BooksReady on creation")
    @Test
    void booksReadyOnCreation(){

        assertThat(order1.getBooksReady(), is(1));
        assertThat(order2.getBooksReady(), is(0));
    }

    @DisplayName("Incrementing books ready")
    @Test
    void incrementBooksReady() {
        order1.incrementBooksReady();
        assertThat(order1.getBooksReady(), is(2));
    }

    @DisplayName("Confirming book reception increments booksReady")
    @Test
    void confirmingBookReception() {
        order1.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        assertThat(order1.getBooksReady(), is(2));
    }

    @DisplayName("Status updates to WaitingForRider when all books ready")
    @Test
    void incrementBooksReadyCallsCheckOrderReady() {
        order1.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        assertThat(order1.getStatusId(), is(2));

    }

    @Test
    void setRider(){
        order1.setRider(rider1.getId());
        assertThat(order1.getRiderId(), is((long)1));

    }

    @DisplayName("Delivering Order when order is ready")
    @Test
    void performDeliverOnWaitingForRider() {
        order3.performOperation(Operation.DELIVER);
        assertThat(order3.getStatusId(), is(3));

    }

    @DisplayName("Delivering Order when order is not ready")
    @Test
    void performDeliverOnInvalidStatus() {
        assertThrows(IllegalStateException.class, ()->order2.performOperation(Operation.DELIVER));
        assertFalse(order2.getStatusId()==3);

    }

    @DisplayName("Confirming reception at location when order is EnRouteLocation")
    @Test
    void performConfirmReceptionLocationOnEnRouteLocation() {
        assertThat(order2.getStatusId(), is(1));
        order2.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        //confirming status remains the same because not all books have arrived
        assertFalse(order2.getStatusId()==2);
    }

    @DisplayName("Confirming reception at location when order is not EnRouteLocation")
    @Test
    void performConfirmReceptionLocationOnInvalidStatus() {
        assertThrows(IllegalStateException.class, ()->order3.performOperation(Operation.CONFIRM_RECEPTION_LOCATION));
        //Confirming that booksReady hasn't been incremented
        assertThat(order3.getBooksReady(), is(2));
    }

    @DisplayName("Confirming reception at location when order is EnRouteLocation")
    @Test
    void performConfirmReceptionClientOnEnRouteClient() {
        order3.performOperation(Operation.DELIVER);
        order3.performOperation(Operation.CONFIRM_RECEPTION_CLIENT);
        assertThat(order3.getStatusId(), is(4));

    }

    @DisplayName("Confirming reception at location when order is not EnRouteLocation")
    @Test
    void performConfirmReceptionClientOnInvalidStatus() {
        //Checking For Status == EnRouteLocation
        assertThrows(IllegalStateException.class, ()->order1.performOperation(Operation.CONFIRM_RECEPTION_CLIENT));
        //Checking for status == WaitingFOrRIder
        order1.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        assertThrows(IllegalStateException.class, ()->order1.performOperation(Operation.CONFIRM_RECEPTION_CLIENT));
        assertFalse(order1.getStatusId()==4);


    }

    @DisplayName("Checking status is possible in any orderStatus")
    @Test
    void performingStatusCheckOnEveryOrderStatus(){
        order1.performOperation(Operation.CHECK_STATUS);
        assertThat(order1.getStatusId(), is(1));


        order1.performOperation(Operation.CONFIRM_RECEPTION_LOCATION);
        order1.performOperation(Operation.CHECK_STATUS);
        assertThat(order1.getStatusId(), is(2));


        order1.performOperation(Operation.DELIVER);
        order1.performOperation(Operation.CHECK_STATUS);
        assertThat(order1.getStatusId(), is(3));


        order1.performOperation(Operation.CONFIRM_RECEPTION_CLIENT);
        order1.performOperation(Operation.CHECK_STATUS);
        assertThat(order1.getStatusId(), is(4));


    }

}