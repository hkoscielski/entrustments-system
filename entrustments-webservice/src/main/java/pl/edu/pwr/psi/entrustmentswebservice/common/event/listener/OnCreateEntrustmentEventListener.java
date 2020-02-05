package pl.edu.pwr.psi.entrustmentswebservice.common.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.edu.pwr.psi.entrustmentswebservice.common.event.OnCreateEntrustmentEvent;
import pl.edu.pwr.psi.entrustmentswebservice.common.service.MailService;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;

import javax.mail.MessagingException;

@Component
@Slf4j
public class OnCreateEntrustmentEventListener implements ApplicationListener<OnCreateEntrustmentEvent> {

	@Autowired
	private MailService mailService;

	@Override
	@Async
	public void onApplicationEvent(OnCreateEntrustmentEvent onCreateEntrustmentEvent) {
		sendNotification(onCreateEntrustmentEvent);
	}

	private void sendNotification(OnCreateEntrustmentEvent onCreateEntrustmentEvent) {
		String emailTo = onCreateEntrustmentEvent.getEmail();
		EntrustmentResponseDTO entrustment = onCreateEntrustmentEvent.getEntrustment();
		String message = String.format("Liczba godzin: %s, kurs: %s %s", entrustment.getNumberOfHours(), entrustment.getCourse().getCode(), entrustment.getCourse().getName());

		try {
			mailService.sendEntrustmentNotificationEmail(emailTo, message);
		} catch (MessagingException e) {
			log.error(e.getMessage());
		}
	}
}
