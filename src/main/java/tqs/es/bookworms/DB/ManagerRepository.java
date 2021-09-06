package tqs.es.bookworms.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.es.bookworms.Entities.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
