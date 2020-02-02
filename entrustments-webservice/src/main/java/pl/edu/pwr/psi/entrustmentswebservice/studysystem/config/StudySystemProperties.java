package pl.edu.pwr.psi.entrustmentswebservice.studysystem.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.study-system")
@Getter
@Setter
public class StudySystemProperties {

	private String classpath;
	private String hostname;
	private Integer port;
	private String url;
}
