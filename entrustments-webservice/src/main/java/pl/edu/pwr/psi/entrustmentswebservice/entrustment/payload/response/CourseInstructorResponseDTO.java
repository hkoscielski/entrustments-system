package pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInstructorResponseDTO {

	private long id;
	private String firstName;
	private String surname;
	private String academicDegree;
	private String courseInstructorType;
	private Map<String, String> additionalAttributes;
}
