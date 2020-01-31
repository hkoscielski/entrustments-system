package pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntrustmentResponseDTO {

	private long id;
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
