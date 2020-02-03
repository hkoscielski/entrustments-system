package pl.edu.pwr.psi.entrustmentswebservice.studysystem.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.config.StudySystemProperties;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Component
public class StudySystemExternalService {

	private WireMockServer server;

	public StudySystemExternalService(StudySystemProperties studySystemProperties) {
		this.server = new WireMockServer(
				options().bindAddress(studySystemProperties.getHostname())
						.port(studySystemProperties.getPort())
						.usingFilesUnderClasspath(studySystemProperties.getClasspath())
		);

		server.stubFor(get(urlMatching("/api/course-instructors"))
				.atPriority(1)
				.willReturn(aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("course_instructors.json")));

		server.stubFor(get(urlMatching("/api/faculties/W08/study-plans"))
				.atPriority(1)
				.willReturn(aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("study_plans_w08.json")));
	}

	public void startServer() {
		server.start();
	}
}
