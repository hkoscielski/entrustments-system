package pl.edu.pwr.psi.entrustmentswebservice.common.payload.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Mail {

	private String from;
	private String to;
	private String subject;
	private String content;
}
