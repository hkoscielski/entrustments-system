package pl.edu.pwr.psi.entrustmentswebservice.studysystem.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudyPlanResponseDTO {

	@NotNull
	@Positive
	private Long id;

	@NotNull
	private FacultyResponseDTO faculty;

	@NotNull
	private FieldOfStudyResponseDTO fieldOfStudy;

	@Nullable
	private SpecialtyResponseDTO specialty;

	@NotBlank
	private String studyLevel;

	@NotBlank
	private String formOfStudies;

	@NotBlank
	private String studyLanguage;

	@NotBlank
	private String startAcademicYear;

	@NotBlank
	private String startSemesterName;

	@NotNull
	private Set<SemesterResponseDTO> semesters;

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
	public static class SpecialtyResponseDTO {

		@NotBlank
		private String code;

		@NotBlank
		private String name;

		@NotBlank
		private String shortName;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class SemesterResponseDTO {

		@NotNull
		@Positive
		private Long id;

		@NotBlank
		private String academicYear;

		@NotNull
		@Positive
		private Integer semesterNumber;

		@NotBlank
		private String semesterName;

		@NotNull
		private Integer numberOfStudents;

		@NotNull
		private Set<CourseResponseDTO> courses;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class CourseResponseDTO {

		@Nullable
		private ModuleResponseDTO module;

		@NotBlank
		private String code;

		@NotBlank
		private String name;

		@NotBlank
		private String didacticForm;

		@NotNull
		@Positive
		private Integer zzuHours;

		@NotNull
		@Positive
		private Integer studentsPerGroup;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class ModuleResponseDTO {

		@NotBlank
		private String code;

		@NotBlank
		private String name;
	}
}
