package pl.edu.pwr.psi.entrustmentswebservice.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.psi.entrustmentswebservice.common.config.MailProperties;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.mail.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class MailService {

	@Autowired
	private MailProperties mailProperties;

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEntrustmentNotificationEmail(String to, String message) throws MessagingException {
		Mail mail = Mail.builder()
				.from(mailProperties.getUsername())
				.to(to)
				.subject("System Powierzeń - Otrzymano nowe powierzenie")
				.content("Sprawdź swoją listę powierzeń w systemie.\n" + message)
				.build();
		send(mail);
	}

	private void send(Mail mail) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		javaMailSender.send(message);
	}
}
