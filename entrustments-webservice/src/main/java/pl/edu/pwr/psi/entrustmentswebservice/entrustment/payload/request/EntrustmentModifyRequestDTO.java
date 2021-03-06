package pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntrustmentModifyRequestDTO {

	@NotNull
	@Positive
	private Integer numberOfHours;
}
