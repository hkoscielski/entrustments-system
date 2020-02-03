package pl.edu.pwr.psi.entrustmentswebservice.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.CourseInstructorResponseDTO;
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

	@GetMapping("/fields-of-study/{fieldOfStudyId}/course-instructors")
	public ResponseEntity<List<CourseInstructorResponseDTO>> getCourseInstructors(@PathVariable long fieldOfStudyId) {
		List<CourseInstructorResponseDTO> courseInstructors = courseInstructorService.findCourseInstructorsForFieldOfStudy(fieldOfStudyId);
		return ResponseEntity.ok(courseInstructors);
	}

	@PutMapping("/course-instructors")
	public ResponseEntity<Object> updateCourseInstructors() {
		studySystemService.updateCourseInstructors();
		return ResponseEntity.noContent().build();
	}
}
