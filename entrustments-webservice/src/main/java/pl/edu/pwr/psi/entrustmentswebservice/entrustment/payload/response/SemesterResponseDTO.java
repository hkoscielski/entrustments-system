package pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private String specialty;
	private String studyLevel;
	private String formOfStudy;
	private String studyLanguage;
}
