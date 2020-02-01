package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.EntrustmentPlan;

import java.util.Optional;

@Repository
public interface EntrustmentPlanRepository extends CrudRepository<EntrustmentPlan, Long> {

	Optional<EntrustmentPlan> findBySemesterId(long semesterId);
}
