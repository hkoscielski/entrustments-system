package pl.edu.pwr.psi.entrustmentswebservice.studysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.AcademicDegree;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Course;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.CourseInstructor;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.DidacticForm;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.DoctoralStudent;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Faculty;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.FieldOfStudy;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.FormOfStudy;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Module;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Position;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Semester;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.SemesterName;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Specialist;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Specialty;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLanguage;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLevel;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyPlan;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Teacher;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.User;
import pl.edu.pwr.psi.entrustmentswebservice.common.exception.ResourceInternalServerError;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.*;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.EntrustmentPlan;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository.EntrustmentPlanRepository;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.config.StudySystemProperties;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.payload.response.CourseInstructorResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.payload.response.StudyPlanResponseDTO;

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
	private CourseRepository courseRepository;

	@Autowired
	private CourseInstructorRepository courseInstructorRepository;

	@Autowired
	private DidacticFormRepository didacticFormRepository;

	@Autowired
	private EntrustmentPlanRepository entrustmentPlanRepository;

	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private FieldOfStudyRepository fieldOfStudyRepository;

	@Autowired
	private FormOfStudyRepository formOfStudyRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private SemesterRepository semesterRepository;

	@Autowired
	private SemesterNameRepository semesterNameRepository;

	@Autowired
	private SpecialtyRepository specialtyRepository;

	@Autowired
	private StudyLanguageRepository studyLanguageRepository;

	@Autowired
	private StudyLevelRepository studyLevelRepository;

	@Autowired
	private StudyPlanRepository studyPlanRepository;

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
				FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findByShortNameAndFacultySymbol(instructor.getFieldOfStudy().getShortName(), instructor.getFaculty().getSymbol())
						.orElseThrow(() -> new ResourceInternalServerError(FieldOfStudy.class.getSimpleName()));
				User userToSaveOrUpdate = User.builder()
						.id(instructor.getUserId())
						.email(instructor.getEmail())
						.username(instructor.getUsername())
						.password(instructor.getPassword())
						.fieldOfStudy(fieldOfStudy)
						.build();
				User user = userRepository.save(userToSaveOrUpdate);

				if (Objects.equals(instructor.getCourseInstructorType(), DoctoralStudent.class.getSimpleName())) {
					CourseInstructor doctoralStudent = new DoctoralStudent()
							.setPensum(Integer.parseInt(instructor.getAdditionalAttributes().get("pensum")))
							.setId(instructor.getId())
							.setFirstName(instructor.getFirstName())
							.setSurname(instructor.getSurname())
							.setAcademicDegree(academicDegree)
							.setDidacticForms(didacticForms)
							.setUser(user);
					courseInstructorRepository.save(doctoralStudent);
				} else if (Objects.equals(instructor.getCourseInstructorType(), Specialist.class.getSimpleName())) {
					CourseInstructor specialist = new Specialist()
							.setId(instructor.getId())
							.setFirstName(instructor.getFirstName())
							.setSurname(instructor.getSurname())
							.setAcademicDegree(academicDegree)
							.setDidacticForms(didacticForms)
							.setUser(user);
					courseInstructorRepository.save(specialist);
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
					courseInstructorRepository.save(teacher);
				}
			}
		}
	}

	public void updateStudyPlans(String facultySymbol) {
		@Valid
		StudyPlanResponseDTO[] studyPlans = restTemplate.getForObject(String.format("%s/api/faculties/%s/study-plans", studySystemProperties.getUrl(), facultySymbol), StudyPlanResponseDTO[].class);

		if (studyPlans != null) {
			Map<String, StudyLevel> studyLevelByLevel = studyLevelRepository.findAll()
					.stream()
					.collect(Collectors.toMap(studyLevel -> studyLevel.getCode().name(), Function.identity()));
			Map<String, FormOfStudy> formOfStudyByFormType = formOfStudyRepository.findAll()
					.stream()
					.collect(Collectors.toMap(formOfStudy -> formOfStudy.getCode().name(), Function.identity()));
			Map<String, StudyLanguage> studyLanguageByLanguageCode = studyLanguageRepository.findAll()
					.stream()
					.collect(Collectors.toMap(studyLanguage -> studyLanguage.getCode().name(), Function.identity()));
			Map<String, SemesterName> semesterNameBySemesterType = semesterNameRepository.findAll()
					.stream()
					.collect(Collectors.toMap(semesterName -> semesterName.getCode().name(), Function.identity()));
			Map<String, DidacticForm> didacticFormByCode = didacticFormRepository.findAll()
					.stream()
					.collect(Collectors.toMap(DidacticForm::getCode, Function.identity()));

			for (StudyPlanResponseDTO sp : studyPlans) {
				Faculty facultyToSaveOrUpdate = Faculty.builder()
						.name(sp.getFaculty().getName())
						.symbol(sp.getFaculty().getSymbol())
						.build();
				Faculty faculty = facultyRepository.findBySymbol(sp.getFaculty().getSymbol())
						.map(f -> facultyRepository.save(facultyToSaveOrUpdate.setId(f.getId())))
						.orElse(facultyRepository.save(facultyToSaveOrUpdate));

				FieldOfStudy fieldOfStudyToSaveOrUpdate = FieldOfStudy.builder()
						.name(sp.getFieldOfStudy().getName())
						.shortName(sp.getFieldOfStudy().getShortName())
						.faculty(faculty)
						.build();
				FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findByShortNameAndFacultySymbol(
						sp.getFieldOfStudy().getShortName(),
						sp.getFaculty().getSymbol())
						.map(fos -> fieldOfStudyRepository.save(fieldOfStudyToSaveOrUpdate.setId(fos.getId())))
						.orElse(fieldOfStudyRepository.save(fieldOfStudyToSaveOrUpdate));

				Specialty specialty = null;
				if (sp.getSpecialty() != null) {
					Specialty specialtyToSaveOrUpdate = Specialty.builder()
							.name(sp.getSpecialty().getName())
							.shortName(sp.getSpecialty().getShortName())
							.fieldOfStudy(fieldOfStudy)
							.build();
					specialty = specialtyRepository.findByShortName(sp.getSpecialty().getShortName())
							.map(s -> specialtyRepository.save(specialtyToSaveOrUpdate.setId(s.getId())))
							.orElse(specialtyRepository.save(specialtyToSaveOrUpdate));
				}

				StudyLevel studyLevel = Optional.ofNullable(studyLevelByLevel.getOrDefault(sp.getStudyLevel(), null))
						.orElseThrow(() -> new ResourceInternalServerError(StudyLevel.class.getSimpleName()));
				FormOfStudy formOfStudy = Optional.ofNullable(formOfStudyByFormType.getOrDefault(sp.getFormOfStudies(), null))
						.orElseThrow(() -> new ResourceInternalServerError(FormOfStudy.class.getSimpleName()));
				StudyLanguage studyLanguage = Optional.ofNullable(studyLanguageByLanguageCode.getOrDefault(sp.getStudyLanguage(), null))
						.orElseThrow(() -> new ResourceInternalServerError(StudyLanguage.class.getSimpleName()));
				SemesterName startSemesterName = Optional.ofNullable(semesterNameBySemesterType.getOrDefault(sp.getStartSemesterName(), null))
						.orElseThrow(() -> new ResourceInternalServerError(SemesterName.class.getSimpleName()));

				StudyPlan studyPlanToSaveOrUpdate = StudyPlan.builder()
						.id(sp.getId())
						.fieldOfStudy(fieldOfStudy)
						.specialty(specialty)
						.studyLevel(studyLevel)
						.formOfStudy(formOfStudy)
						.studyLanguage(studyLanguage)
						.startSemesterName(startSemesterName)
						.startAcademicYear(sp.getStartAcademicYear())
						.build();
				StudyPlan studyPlan = studyPlanRepository.save(studyPlanToSaveOrUpdate);

				for (StudyPlanResponseDTO.SemesterResponseDTO s : sp.getSemesters()) {
					SemesterName semesterName = Optional.ofNullable(semesterNameBySemesterType.getOrDefault(s.getSemesterName(), null))
							.orElseThrow(() -> new ResourceInternalServerError(SemesterName.class.getSimpleName()));
					Set<Module> semesterModulesToSaveOrUpdate = new HashSet<>();
					Set<Course> semesterCoursesToSaveOrUpdate = new HashSet<>();

					for (StudyPlanResponseDTO.CourseResponseDTO c : s.getCourses()) {
						Module module = null;
						if (c.getModule() != null) {
							Module moduleToSaveOrUpdate = Module.builder()
									.code(c.getModule().getCode())
									.name(c.getModule().getName())
									.build();
							module = moduleRepository.findByCode(c.getModule().getCode())
									.map(m -> moduleRepository.save(moduleToSaveOrUpdate.setId(m.getId())))
									.orElse(moduleRepository.save(moduleToSaveOrUpdate));
							semesterModulesToSaveOrUpdate.add(module);
						}

						DidacticForm didacticForm = Optional.ofNullable(didacticFormByCode.getOrDefault(c.getDidacticForm(), null))
								.orElseThrow(() -> new ResourceInternalServerError("DidacticForm"));

						Course courseToSaveOrUpdate = Course.builder()
								.module(module)
								.code(c.getCode())
								.name(c.getName())
								.didacticForm(didacticForm)
								.zzuHours(c.getZzuHours())
								.studentsPerGroup(c.getStudentsPerGroup())
								.build();
						Course course = courseRepository.findByCode(c.getCode())
								.map(co -> courseRepository.save(courseToSaveOrUpdate.setId(co.getId())))
								.orElse(courseRepository.save(courseToSaveOrUpdate));
						semesterCoursesToSaveOrUpdate.add(course);
					}

					Semester semesterToSaveOrUpdate = Semester.builder()
							.id(s.getId())
							.academicYear(s.getAcademicYear())
							.semesterNumber(s.getSemesterNumber())
							.semesterName(semesterName)
							.studyPlan(studyPlan)
							.modules(semesterModulesToSaveOrUpdate)
							.courses(semesterCoursesToSaveOrUpdate)
							.build();
					Semester semester = semesterRepository.save(semesterToSaveOrUpdate);

					EntrustmentPlan entrustmentPlanToSaveOrUpdate = EntrustmentPlan.builder()
							.numberOfStudents(s.getNumberOfStudents())
							.semester(semester)
							.build();
					entrustmentPlanRepository.findBySemesterId(semester.getId())
							.map(ep -> entrustmentPlanRepository.save(entrustmentPlanToSaveOrUpdate.setId(ep.getId())))
							.orElse(entrustmentPlanRepository.save(entrustmentPlanToSaveOrUpdate));
				}
			}
		}
	}
}
