package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLevel;

import java.util.Optional;

import static pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLevel.Level;

@Repository
public interface StudyLevelRepository extends JpaRepository<StudyLevel, Long> {

	Optional<StudyLevel> findByCode(Level code);
}
