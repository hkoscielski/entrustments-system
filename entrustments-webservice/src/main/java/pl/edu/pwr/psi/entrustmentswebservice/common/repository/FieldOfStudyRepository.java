package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.FieldOfStudy;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {

	List<FieldOfStudy> findAllByFacultySymbol(String facultySymbol);
	Optional<FieldOfStudy> findByShortNameAndFacultySymbol(String shortName, String facultySymbol);
}
