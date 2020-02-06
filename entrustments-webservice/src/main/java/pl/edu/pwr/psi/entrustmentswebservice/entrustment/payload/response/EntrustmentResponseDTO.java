package pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.CourseResponseDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class EntrustmentResponseDTO {

	private long id;
	private long semesterId;
	private int numberOfHours;
	private CourseResponseDTO course;
	private CourseInstructorResponseDTO courseInstructor;
	private String status;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class CourseInstructorResponseDTO {

		private long id;
		private String firstName;
		private String surname;
		private String academicDegree;
	}
}
