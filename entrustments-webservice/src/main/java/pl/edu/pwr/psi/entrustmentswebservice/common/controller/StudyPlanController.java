package pl.edu.pwr.psi.entrustmentswebservice.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.FacultyResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.SemesterResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.StudyPlanService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudyPlanController {

	@Autowired
	private StudyPlanService studyPlanService;

	@GetMapping("/faculties")
	public ResponseEntity<List<FacultyResponseDTO>> getFaculties() {
		List<FacultyResponseDTO> faculties = studyPlanService.findAllFaculties();
		return ResponseEntity.ok(faculties);
	}

	@GetMapping("/semesters")
	public ResponseEntity<List<SemesterResponseDTO>> getSemesters(@RequestParam("facultyId") long facultyId) {
		List<SemesterResponseDTO> semesters = studyPlanService.findActualSemestersForFaculty(facultyId);
		return ResponseEntity.ok(semesters);
	}
}
