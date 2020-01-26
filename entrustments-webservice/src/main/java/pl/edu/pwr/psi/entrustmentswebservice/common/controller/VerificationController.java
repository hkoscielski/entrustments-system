package pl.edu.pwr.psi.entrustmentswebservice.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VerificationController {

	@GetMapping("/verify")
	public ResponseEntity<String> getVerificationStatus() {
		return ResponseEntity.ok("OK");
	}
}
