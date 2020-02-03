package pl.edu.pwr.psi.entrustmentswebservice.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.FacultyResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.SemesterResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.StudyPlanService;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.service.StudySystemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudyPlanController {

	@Autowired
	private StudyPlanService studyPlanService;

	@Autowired
	private StudySystemService studySystemService;

	@GetMapping("/faculties")
	public ResponseEntity<List<FacultyResponseDTO>> getFaculties() {
		List<FacultyResponseDTO> faculties = studyPlanService.findAllFaculties();
		return ResponseEntity.ok(faculties);
	}

	@PutMapping("/faculties/{facultySymbol}/study-plans")
	public ResponseEntity<Object> updateStudyPlansForFaculty(@PathVariable String facultySymbol) {
		studySystemService.updateStudyPlans(facultySymbol);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/semesters")
	public ResponseEntity<List<SemesterResponseDTO>> getSemesters(@RequestParam("facultyId") long facultyId) {
		List<SemesterResponseDTO> semesters = studyPlanService.findActualSemestersForFaculty(facultyId);
		return ResponseEntity.ok(semesters);
	}
}
