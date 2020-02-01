package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.EntrustmentLog;

@Repository
public interface EntrustmentLogRepository extends CrudRepository<EntrustmentLog, Long> {
}
