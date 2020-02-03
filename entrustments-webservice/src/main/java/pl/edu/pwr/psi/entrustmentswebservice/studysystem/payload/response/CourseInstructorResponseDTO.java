package pl.edu.pwr.psi.entrustmentswebservice.studysystem.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInstructorResponseDTO {

	@NotNull
	@Positive
	private Long id;

	@NotNull
	@Positive
	private Long userId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String surname;

	@Email
	private String email;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotNull
	private FacultyResponseDTO faculty;

	@NotNull
	private FieldOfStudyResponseDTO fieldOfStudy;

	@NotBlank
	private String academicDegree;

	@NotNull
	private Set<String> didacticForms;

	@NotNull
	private List<AgreementResponseDTO> agreements;

	@NotBlank
	private String courseInstructorType;

	@NotNull
	private Map<String, String> additionalAttributes;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class FacultyResponseDTO {

		@NotBlank
		private String symbol;

		@NotBlank
		private String name;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class FieldOfStudyResponseDTO {

		@NotBlank
		private String name;

		@NotBlank
		private String shortName;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class AgreementResponseDTO {

		@NotBlank
		private String didacticForm;

		@NotNull
		private LocalDateTime startDate;

		@NotNull
		private LocalDateTime endDate;
	}
}
