package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLanguage;

import java.util.Optional;

import static pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLanguage.LanguageCode;

@Repository
public interface StudyLanguageRepository extends JpaRepository<StudyLanguage, Long> {

	Optional<StudyLanguage> findByCode(LanguageCode code);
}
