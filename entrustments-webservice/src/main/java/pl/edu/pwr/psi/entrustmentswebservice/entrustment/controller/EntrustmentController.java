package pl.edu.pwr.psi.entrustmentswebservice.entrustment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.EntrustmentService;

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
}
