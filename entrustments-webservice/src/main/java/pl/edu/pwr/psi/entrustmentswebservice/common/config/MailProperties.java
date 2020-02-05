package pl.edu.pwr.psi.entrustmentswebservice.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.mail")
@Getter
@Setter
public class MailProperties {

	private String host;
	private Integer port;
	private String username;
	private String password;
	private String defaultEncoding;
	private String protocol;
	private Properties properties;

	@Getter
	@Setter
	public static class Properties {

		private Mail mail;

		@Getter
		@Setter
		public static class Mail {

			private Boolean debug;
			private Smtp smtp;

			@Getter
			@Setter
			public static class Smtp {

				private String auth;
				private Starttls starttls;

				@Getter
				@Setter
				public static class Starttls {

					private Boolean enable;
				}
			}
		}
	}
}
