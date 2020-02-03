package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.EntrustmentStatus;

import java.util.Optional;

import static pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.EntrustmentStatus.StatusCode;

@Repository
public interface EntrustmentStatusRepository extends CrudRepository<EntrustmentStatus, Long> {

	Optional<EntrustmentStatus> findByCode(StatusCode code);
}
