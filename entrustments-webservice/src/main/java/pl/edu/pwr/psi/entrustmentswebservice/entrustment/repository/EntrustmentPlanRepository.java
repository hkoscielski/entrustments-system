package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.EntrustmentPlan;

import java.util.Optional;

@Repository
public interface EntrustmentPlanRepository extends CrudRepository<EntrustmentPlan, Long> {

	Optional<EntrustmentPlan> findBySemesterId(long semesterId);

	@Query("select ep from EntrustmentPlan ep join fetch ep.semester where ep.id = :entrustment_plan_id")
	Optional<EntrustmentPlan> findByEntrustmentPlanId(@Param("entrustment_plan_id") long entrustmentPlanId);
}
