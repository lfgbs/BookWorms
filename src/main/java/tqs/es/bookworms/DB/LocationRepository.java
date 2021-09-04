package tqs.es.bookworms.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.es.bookworms.Entities.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
