package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Semester;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

	@Query("select s from Semester s join s.studyPlan " +
			"where s.studyPlan.fieldOfStudy.id = :field_of_study_id " +
			"and cast(substring(s.academicYear, 1, 4) as int) >= :year")
	List<Semester> findAllSemestersForFieldOfStudyAfterDate(
			@Param("field_of_study_id") long fieldOfStudyId,
			@Param("year") int year
	);
}
