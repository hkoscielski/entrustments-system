package pl.edu.pwr.psi.entrustmentswebservice.entrustment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.CourseInstructorResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.CourseInstructorService;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.service.StudySystemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CourseInstructorController {

	@Autowired
	private CourseInstructorService courseInstructorService;

	@Autowired
	private StudySystemService studySystemService;

	@GetMapping("/course-instructors")
	public ResponseEntity<List<CourseInstructorResponseDTO>> getCourseInstructors() {
		List<CourseInstructorResponseDTO> courseInstructors = courseInstructorService.findAllCourseInstructors();
		return ResponseEntity.ok(courseInstructors);
	}

	@PutMapping("/course-instructors")
	public ResponseEntity<Object> updateCourseInstructors() {
		studySystemService.updateCourseInstructors();
		return ResponseEntity.noContent().build();
	}
}
