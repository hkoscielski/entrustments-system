package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.Entrustment;

@Repository
public interface EntrustmentRepository extends CrudRepository<Entrustment, Long> {
}
