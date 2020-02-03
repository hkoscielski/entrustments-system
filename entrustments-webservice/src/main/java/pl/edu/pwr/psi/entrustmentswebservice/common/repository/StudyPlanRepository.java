package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyPlan;

@Repository
public interface StudyPlanRepository extends CrudRepository<StudyPlan, Long> {

}
