package tqs.es.bookworms.Entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tqs.es.bookworms.Controllers.OrderController;
import tqs.es.bookworms.Controllers.RiderController;
import tqs.es.bookworms.Exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RiderTest {

    @Mock
    private Location location1;
    @Mock
    private Order order1;
    @Mock
    private Order order2;
    @Mock
    private OrderController orderController;

    private Rider rider1 = new Rider("email", "Rui");

    @org.junit.jupiter.api.BeforeEach
    void setUp(){
        Mockito.when( location1.getId()).thenReturn((long) 1);
        Mockito.when( order1.getId()).thenReturn((long) 1);
        Mockito.when( order2.getId()).thenReturn((long) 2);
        Mockito.when( orderController.getStatus(order2.getId())).thenReturn(new WaitingForRider());
        Mockito.when( orderController.getStatus(order1.getId())).thenReturn(new EnRouteLocation());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @DisplayName("Changing Location")
    @Test
    void setLocation() {
        assertTrue(rider1.getLocationId()==null);
        rider1.setLocationId(location1.getId());
        assertThat(rider1.getLocationId(), is((long)1));
    }

    @DisplayName("Checking Status on creation")
    @Test
    void getStatusOnCreation() {
        assertTrue(rider1.getStatus()==RiderStatus.AVAILABLE);
    }

    @DisplayName("Delivering Order that is ready is possible and changes RiderState to Occupied")
    @Test
    void deliverOrderWhileOrderReady() {
        rider1.deliverOrder(order2.getId());
        assertTrue(rider1.getStatus()==RiderStatus.OCCUPIED);
    }

    @DisplayName("Delivering Order Sets orderId on Rider")
    @Test
    void deliverOrderSetsOrderId() {
        rider1.deliverOrder(order2.getId());
        assertTrue(rider1.getOrderId() == order2.getId());
    }

    /*
    @DisplayName("Delivering Order that is not ready")
    @Test
    void deliverOrderWhileOrderNotReady() {
        assertThrows(IllegalStateException.class, ()->rider1.deliverOrder(order1.getId()));
        assertTrue(rider1.getStatus()==RiderStatus.AVAILABLE);
    }

     */


    @DisplayName("Completing delivery changes status to AVAILABLE")
    @Test
    void checkStatusWhileOrderDelivered() {
        rider1.deliverOrder(order2.getId());
        assertTrue(rider1.getStatus()==RiderStatus.OCCUPIED);
        rider1.deliveryComplete();
        assertTrue(rider1.getStatus()==RiderStatus.AVAILABLE);
        //check if orderId was wiped
        assertTrue(rider1.getOrderId() == null);


    }


}