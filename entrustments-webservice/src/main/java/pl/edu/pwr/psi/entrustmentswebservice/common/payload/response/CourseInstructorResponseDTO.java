package pl.edu.pwr.psi.entrustmentswebservice.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInstructorResponseDTO {

	private long id;
	private String firstName;
	private String surname;
	private String academicDegree;
	private Set<AgreementResponseDTO> agreements;
	private String courseInstructorType;
	private Map<String, String> additionalAttributes;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class AgreementResponseDTO {

		@NotNull
		private LocalDateTime startDate;

		@NotNull
		private LocalDateTime endDate;

		@NotBlank
		private String didacticForm;
	}
}
