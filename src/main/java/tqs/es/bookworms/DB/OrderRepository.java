package tqs.es.bookworms.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.es.bookworms.Entities.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {
}
