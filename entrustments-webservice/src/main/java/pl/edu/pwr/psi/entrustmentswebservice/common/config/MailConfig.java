package pl.edu.pwr.psi.entrustmentswebservice.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Properties;

@Configuration
@EnableAsync
public class MailConfig {

	@Autowired
	private MailProperties mailProperties;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailProperties.getHost());
		mailSender.setPort(mailProperties.getPort());
		mailSender.setUsername(mailProperties.getUsername());
		mailSender.setPassword(mailProperties.getPassword());
		mailSender.setDefaultEncoding(mailProperties.getDefaultEncoding());

		Properties properties = new Properties();
		properties.put("mail.debug", mailProperties.getProperties().getMail().getDebug());
		properties.put("mail.smtp.auth", mailProperties.getProperties().getMail().getSmtp().getAuth());
		properties.put("mail.transport.protocol", mailProperties.getProtocol());
		properties.put("mail.smtp.starttls.enable", mailProperties.getProperties().getMail().getSmtp().getStarttls().getEnable());
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}
}
