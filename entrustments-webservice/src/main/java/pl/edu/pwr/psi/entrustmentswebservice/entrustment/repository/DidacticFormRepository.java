package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.DidacticForm;

@Repository
public interface DidacticFormRepository extends JpaRepository<DidacticForm, Long> {
}
