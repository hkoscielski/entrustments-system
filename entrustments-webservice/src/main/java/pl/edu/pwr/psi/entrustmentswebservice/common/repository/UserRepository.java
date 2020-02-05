package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

	@Query("select ci.user from CourseInstructor ci where ci.id = :course_instructor_id")
	Optional<User> findByCourseInstructorId(@Param("course_instructor_id") long courseInstructorId);
}
