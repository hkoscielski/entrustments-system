package pl.edu.pwr.psi.entrustmentswebservice.entrustment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.CourseInstructor;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.CourseInstructorResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository.CourseInstructorRepository;

import java.util.List;

@Service
public class CourseInstructorService {

	@Autowired
	private CourseInstructorRepository courseInstructorRepository;

	@Autowired
	private ComplexModelMapper complexModelMapper;

	public List<CourseInstructorResponseDTO> findAllCourseInstructors() {
		List<CourseInstructor> courseInstructors = courseInstructorRepository.findAll();
		return complexModelMapper.mapAll(courseInstructors, CourseInstructorResponseDTO.class);
	}
}
