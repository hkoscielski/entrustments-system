package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

	Optional<Course> findByCode(String code);
}
