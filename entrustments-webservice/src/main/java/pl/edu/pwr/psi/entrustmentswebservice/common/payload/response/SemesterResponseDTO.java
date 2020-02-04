package pl.edu.pwr.psi.entrustmentswebservice.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SemesterResponseDTO {

	private long id;
	private String academicYear;
	private String semesterName;
	private int semesterNumber;
	private String startAcademicYear;
	private String startSemesterName;
	private FieldOfStudyResponseDTO fieldOfStudy;
	private SpecialtyResponseDTO specialty;
	private StudyLevelResponseDTO studyLevel;
	private FormOfStudyResponseDTO formOfStudy;
	private StudyLanguageResponseDTO studyLanguage;
	private Set<CourseResponseDTO> courses;
	private Set<ModuleResponseDTO> modules;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class SpecialtyResponseDTO {

		private String name;
		private String shortName;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class StudyLevelResponseDTO {

		private String code;
		private String name;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class FormOfStudyResponseDTO {

		private String code;
		private String name;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class StudyLanguageResponseDTO {

		private String code;
		private String name;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class CourseResponseDTO {

		private long id;
		private String code;
		private String name;
		private int zzuHours;
		private DidacticFormResponseDTO didacticForm;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class DidacticFormResponseDTO {

		private String code;
		private String name;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class ModuleResponseDTO {

		private String code;
		private String name;
		private Set<CourseResponseDTO> courses;
	}
}
