package pl.edu.pwr.psi.entrustmentswebservice.entrustment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.request.EntrustmentRequestDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.EntrustmentService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EntrustmentController {

	@Autowired
	private EntrustmentService entrustmentService;

	@GetMapping("/entrustments")
	public ResponseEntity<List<EntrustmentResponseDTO>> getActualEntrustments(EntrustmentCriteriaDTO entrustmentCriteria) {
		List<EntrustmentResponseDTO> entrustments = entrustmentService.findAllEntrustments(entrustmentCriteria);
		return ResponseEntity.ok(entrustments);
	}

	@PostMapping("/semesters/{semesterId}/entrustments")
	public ResponseEntity<Object> createEntrustment(
			@PathVariable Long semesterId,
			@Valid @RequestBody EntrustmentRequestDTO entrustmentBody
	) {
		EntrustmentResponseDTO entrustment = entrustmentService.createEntrustment(semesterId, entrustmentBody);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(entrustment.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/semesters/{semesterId}/entrustments/{entrustmentId}/accept")
	public ResponseEntity<Object> acceptEntrustment(@PathVariable Long semesterId, @PathVariable Long entrustmentId) {
		entrustmentService.acceptEntrustment(semesterId, entrustmentId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/semesters/{semesterId}/entrustments/{entrustmentId}/reject")
	public ResponseEntity<Object> rejectEntrustment(@PathVariable Long semesterId, @PathVariable Long entrustmentId) {
		entrustmentService.rejectEntrustment(semesterId, entrustmentId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/entrustments/hours")
	public ResponseEntity<Integer> getSum(@RequestParam("courseInstructorId") Long courseInstructorId) {
		return ResponseEntity.ok(entrustmentService.findSumOfEntrustmentsHoursForCourseInstructor(courseInstructorId));
	}
}
