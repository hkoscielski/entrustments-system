package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.FormOfStudy;

import java.util.Optional;

import static pl.edu.pwr.psi.entrustmentswebservice.common.entity.FormOfStudy.FormType;

@Repository
public interface FormOfStudyRepository extends JpaRepository<FormOfStudy, Long> {

	Optional<FormOfStudy> findByCode(FormType code);
}
