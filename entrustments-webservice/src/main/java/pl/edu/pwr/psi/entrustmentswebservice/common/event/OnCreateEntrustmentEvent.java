package pl.edu.pwr.psi.entrustmentswebservice.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;

@Getter
public class OnCreateEntrustmentEvent extends ApplicationEvent {

	private transient String email;
	private transient EntrustmentResponseDTO entrustment;

	public OnCreateEntrustmentEvent(Object source, String email, EntrustmentResponseDTO entrustment) {
		super(source);
		this.email = email;
		this.entrustment = entrustment;
	}
}
