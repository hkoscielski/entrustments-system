package pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntrustmentCriteriaDTO {

	private String academicYear;
	private Integer semester;
	private String studyLevel;
	private String specialty;
	private String courseCode;
}
