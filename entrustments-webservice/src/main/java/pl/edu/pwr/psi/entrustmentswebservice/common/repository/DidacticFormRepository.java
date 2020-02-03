package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.DidacticForm;

@Repository
public interface DidacticFormRepository extends JpaRepository<DidacticForm, Long> {
}
