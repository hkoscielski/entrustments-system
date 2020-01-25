package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.CourseInstructor;

@Repository
public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {

}
