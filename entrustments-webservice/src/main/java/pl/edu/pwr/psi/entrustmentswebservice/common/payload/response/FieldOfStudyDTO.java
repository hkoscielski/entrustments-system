package pl.edu.pwr.psi.entrustmentswebservice.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldOfStudyDTO {

	private long id;
	private String name;
	private String shortName;
}
