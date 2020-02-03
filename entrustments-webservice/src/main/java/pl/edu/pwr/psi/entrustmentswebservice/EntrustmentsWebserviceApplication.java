package pl.edu.pwr.psi.entrustmentswebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.service.StudySystemExternalService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class EntrustmentsWebserviceApplication {

	private final StudySystemExternalService studySystemExternalService;

	public EntrustmentsWebserviceApplication(StudySystemExternalService studySystemExternalService) {
		this.studySystemExternalService = studySystemExternalService;
	}

	public static void main(String[] args) {
		SpringApplication.run(EntrustmentsWebserviceApplication.class, args);
	}

	@PostConstruct
	private void runMockServer() {
		studySystemExternalService.startServer();
	}
}
