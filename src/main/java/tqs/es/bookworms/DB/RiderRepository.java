package tqs.es.bookworms.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.es.bookworms.Entities.Rider;

public interface RiderRepository  extends JpaRepository<Rider, Long> {
}
