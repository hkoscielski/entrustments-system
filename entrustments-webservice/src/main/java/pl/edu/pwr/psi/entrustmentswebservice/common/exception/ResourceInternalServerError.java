package pl.edu.pwr.psi.entrustmentswebservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceInternalServerError extends RuntimeException {

	public ResourceInternalServerError(String resource) {
		super(String.format("Static resource %s not found", resource));
	}
}
