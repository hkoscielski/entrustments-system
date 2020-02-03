package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.CourseInstructor;

import java.util.List;

@Repository
public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {

	@Query("select ci from CourseInstructor ci where ci.user.fieldOfStudy.id = :field_of_study_id or ci.user.fieldOfStudy is null")
	List<CourseInstructor> findByIdFieldOfStudy(@Param("field_of_study_id") long fieldOfStudyId);
}
