package pl.edu.pwr.psi.entrustmentswebservice.entrustment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pwr.psi.entrustmentswebservice.common.event.OnCreateEntrustmentEvent;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.UserResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.request.EntrustmentCreateRequestDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.request.EntrustmentModifyRequestDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentForInstructorCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.CourseInstructorService;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.EntrustmentService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class EntrustmentController {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private CourseInstructorService courseInstructorService;

	@Autowired
	private EntrustmentService entrustmentService;

	@GetMapping("/course-instructors/{courseInstructorId}/entrustments")
	public ResponseEntity<List<EntrustmentResponseDTO>> getActualEntrustmentsForCourseInstructor(
			@PathVariable Long courseInstructorId,
			EntrustmentForInstructorCriteriaDTO entrustmentCriteria
	) {
		List<EntrustmentResponseDTO> entrustments = entrustmentService.findAllEntrustmentsForCourseInstructor(courseInstructorId, entrustmentCriteria);
		return ResponseEntity.ok(entrustments);
	}

	@GetMapping("/entrustments")
	public ResponseEntity<List<EntrustmentResponseDTO>> getActualEntrustments(EntrustmentCriteriaDTO entrustmentCriteria) {
		List<EntrustmentResponseDTO> entrustments = entrustmentService.findAllEntrustments(entrustmentCriteria);
		return ResponseEntity.ok(entrustments);
	}

	@GetMapping("/course-instructors/{courseInstructorId}/entrustments/hours")
	public ResponseEntity<Integer> getEntrustedHours(@PathVariable Long courseInstructorId) {
		return ResponseEntity.ok(entrustmentService.findSumOfEntrustmentsHoursForCourseInstructor(courseInstructorId));
	}

	@PostMapping("/semesters/{semesterId}/entrustments")
	public ResponseEntity<EntrustmentResponseDTO> createEntrustment(
			@PathVariable Long semesterId,
			@Valid @RequestBody EntrustmentCreateRequestDTO entrustmentBody
	) {
		EntrustmentResponseDTO entrustment = entrustmentService.createEntrustment(semesterId, entrustmentBody);
		UserResponseDTO user = courseInstructorService.findUserForCourseInstructor(entrustment.getCourseInstructor().getId());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(entrustment.getId())
				.toUri();
		OnCreateEntrustmentEvent onCreateEntrustmentEvent = new OnCreateEntrustmentEvent(this, user.getEmail(), entrustment);
		applicationEventPublisher.publishEvent(onCreateEntrustmentEvent);

		return ResponseEntity.created(location).body(entrustment);
	}

	@PatchMapping("/semesters/{semesterId}/entrustments/{entrustmentId}")
	public ResponseEntity<Object> modifyEntrustment(
			@PathVariable Long semesterId,
			@PathVariable Long entrustmentId,
			@Valid @RequestBody EntrustmentModifyRequestDTO entrustmentBody
	) {
		EntrustmentResponseDTO entrustment = entrustmentService.modifyEntrustment(semesterId, entrustmentId, entrustmentBody);
		return ResponseEntity.ok(entrustment);
	}

	@PatchMapping("/semesters/{semesterId}/entrustments/{entrustmentId}/accept")
	public ResponseEntity<EntrustmentResponseDTO> acceptEntrustment(@PathVariable Long semesterId, @PathVariable Long entrustmentId) {
		EntrustmentResponseDTO entrustment = entrustmentService.acceptEntrustment(semesterId, entrustmentId);
		return ResponseEntity.ok(entrustment);
	}

	@PatchMapping("/semesters/{semesterId}/entrustments/{entrustmentId}/reject")
	public ResponseEntity<Object> rejectEntrustment(@PathVariable Long semesterId, @PathVariable Long entrustmentId) {
		EntrustmentResponseDTO entrustment = entrustmentService.rejectEntrustment(semesterId, entrustmentId);
		return ResponseEntity.ok(entrustment);
	}
}
