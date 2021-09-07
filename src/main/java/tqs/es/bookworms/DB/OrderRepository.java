package tqs.es.bookworms.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.es.bookworms.Entities.Order;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    List<Order> findOrdersByLocationId(Long locationId);
    List<Order> findOrdersByBuyerId(Long buyerId);
    List<Order> findOrdersByLocationIdAndStatusId(Long locationId, int statusId);
    List<Order> findOrdersByStatusId(int statusId);



}
