package pl.edu.pwr.psi.entrustmentswebservice.studysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.*;
import pl.edu.pwr.psi.entrustmentswebservice.common.exception.ResourceInternalServerError;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.*;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.config.StudySystemProperties;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.payload.response.CourseInstructorResponseDTO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudySystemService {

	@Autowired
	private AcademicDegreeRepository academicDegreeRepository;

	@Autowired
	private CourseInstructorRepository courseInstructorRepository;

	@Autowired
	private DidacticFormRepository didacticFormRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StudySystemProperties studySystemProperties;

	public void updateCourseInstructors() {
		@Valid
		CourseInstructorResponseDTO[] instructors = restTemplate.getForObject(String.format("%s/api/course-instructors", studySystemProperties.getUrl()), CourseInstructorResponseDTO[].class);

		if (instructors != null) {
			List<User> usersToSaveOrUpdate = new ArrayList<>();
			List<CourseInstructor> instructorsToSaveOrUpdate = new ArrayList<>();

			Map<String, AcademicDegree> academicDegreeByShortName = academicDegreeRepository.findAll()
					.stream()
					.collect(Collectors.toMap(AcademicDegree::getShortName, Function.identity()));
			Map<String, DidacticForm> didacticFormsByCode = didacticFormRepository.findAll()
					.stream()
					.collect(Collectors.toMap(DidacticForm::getCode, Function.identity()));
			Map<String, Position> positionByName = positionRepository.findAll()
					.stream()
					.collect(Collectors.toMap(Position::getName, Function.identity()));

			for (CourseInstructorResponseDTO instructor : instructors) {
				AcademicDegree academicDegree = Optional.ofNullable(academicDegreeByShortName.getOrDefault(instructor.getAcademicDegree(), null))
						.orElseThrow(() -> new ResourceInternalServerError("AcademicDegree"));
				Set<DidacticForm> didacticForms = didacticFormsByCode.entrySet().stream()
						.filter(s -> instructor.getDidacticForms().contains(s.getKey()))
						.map(Map.Entry::getValue)
						.collect(Collectors.toSet());
				User user = User.builder()
						.id(instructor.getUserId())
						.email(instructor.getEmail())
						.username(instructor.getUsername())
						.password(instructor.getPassword())
						.build();
				usersToSaveOrUpdate.add(user);

				if (Objects.equals(instructor.getCourseInstructorType(), DoctoralStudent.class.getSimpleName())) {
					CourseInstructor doctoralStudent = new DoctoralStudent()
							.setPensum(Integer.parseInt(instructor.getAdditionalAttributes().get("pensum")))
							.setId(instructor.getId())
							.setFirstName(instructor.getFirstName())
							.setSurname(instructor.getSurname())
							.setAcademicDegree(academicDegree)
							.setDidacticForms(didacticForms)
							.setUser(user);
					instructorsToSaveOrUpdate.add(doctoralStudent);
				} else if (Objects.equals(instructor.getCourseInstructorType(), Specialist.class.getSimpleName())) {
					CourseInstructor specialist = new Specialist()
							.setId(instructor.getId())
							.setFirstName(instructor.getFirstName())
							.setSurname(instructor.getSurname())
							.setAcademicDegree(academicDegree)
							.setDidacticForms(didacticForms)
							.setUser(user);
					instructorsToSaveOrUpdate.add(specialist);
				} else if (Objects.equals(instructor.getCourseInstructorType(), Teacher.class.getSimpleName())) {
					Position position = Optional.ofNullable(positionByName.getOrDefault(instructor.getAdditionalAttributes().get("position"), null))
							.orElseThrow(() -> new ResourceInternalServerError("Position"));
					CourseInstructor teacher = new Teacher()
							.setPensum(Integer.parseInt(instructor.getAdditionalAttributes().get("pensum")))
							.setTimeBasis(new BigDecimal(instructor.getAdditionalAttributes().get("timeBasis")))
							.setPosition(position)
							.setId(instructor.getId())
							.setFirstName(instructor.getFirstName())
							.setSurname(instructor.getSurname())
							.setAcademicDegree(academicDegree)
							.setDidacticForms(didacticForms)
							.setUser(user);
					instructorsToSaveOrUpdate.add(teacher);
				}
			}

			userRepository.saveAll(usersToSaveOrUpdate);
			courseInstructorRepository.saveAll(instructorsToSaveOrUpdate);
		}
	}
}
