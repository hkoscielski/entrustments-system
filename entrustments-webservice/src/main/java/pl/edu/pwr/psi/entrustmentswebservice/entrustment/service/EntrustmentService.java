package pl.edu.pwr.psi.entrustmentswebservice.entrustment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Course;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.CourseInstructor;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Semester;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.User;
import pl.edu.pwr.psi.entrustmentswebservice.common.exception.ResourceInternalServerError;
import pl.edu.pwr.psi.entrustmentswebservice.common.exception.ResourceNotFoundException;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.CourseInstructorRepository;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.CourseRepository;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.UserRepository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.*;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.request.EntrustmentCreateRequestDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.request.EntrustmentModifyRequestDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentForInstructorCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EntrustmentService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CourseInstructorRepository courseInstructorRepository;

	@Autowired
	private EntrustmentRepository entrustmentRepository;

	@Autowired
	private EntrustmentLogRepository entrustmentLogRepository;

	@Autowired
	private EntrustmentPlanRepository entrustmentPlanRepository;

	@Autowired
	private EntrustmentStatusRepository entrustmentStatusRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VEntrustmentRepository ventrustmentRepository;

	@Autowired
	private ComplexModelMapper complexModelMapper;

	@Transactional(readOnly = true)
	public List<EntrustmentResponseDTO> findAllEntrustments(EntrustmentCriteriaDTO entrustmentCriteria) {
		List<VEntrustment> entrustments = ventrustmentRepository.findAllEntrustmentsByPlanAndFilters(
				entrustmentCriteria.getAcademicYear(),
				entrustmentCriteria.getSemester(),
				entrustmentCriteria.getStudyLevel(),
				entrustmentCriteria.getSpecialty(),
				entrustmentCriteria.getCourseCode(),
				entrustmentCriteria.getCourseInstructorId(),
				entrustmentCriteria.getEntrustmentStatus()
		);
		Map<Long, Long> entrustmentPlanIdsByEntrustmentId = entrustments.stream()
				.collect(Collectors.toMap(ve -> ve.getId().getEntrustment().getId(), ve -> ve.getId().getEntrustment().getEntrustmentPlan().getId()));
		return complexModelMapper.mapAll(entrustments, EntrustmentResponseDTO.class)
				.stream().map(e -> e.setSemesterId(entrustmentPlanRepository.findByEntrustmentPlanId(entrustmentPlanIdsByEntrustmentId.get(e.getId())).map(ep -> ep.getSemester().getId()).orElseThrow(() -> new ResourceInternalServerError(Semester.class.getSimpleName()))))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<EntrustmentResponseDTO> findAllEntrustmentsForCourseInstructor(Long courseInstructorId, EntrustmentForInstructorCriteriaDTO entrustmentCriteria) {
		List<VEntrustment> entrustments = ventrustmentRepository.findAllEntrustmentsByPlanAndFilters(
				entrustmentCriteria.getAcademicYear(),
				entrustmentCriteria.getSemester(),
				entrustmentCriteria.getStudyLevel(),
				entrustmentCriteria.getSpecialty(),
				entrustmentCriteria.getCourseCode(),
				courseInstructorId,
				entrustmentCriteria.getEntrustmentStatus()
		);
		Map<Long, Long> entrustmentPlanIdsByEntrustmentId = entrustments.stream()
				.collect(Collectors.toMap(ve -> ve.getId().getEntrustment().getId(), ve -> ve.getId().getEntrustment().getEntrustmentPlan().getId()));
		return complexModelMapper.mapAll(entrustments, EntrustmentResponseDTO.class)
				.stream().map(e -> e.setSemesterId(entrustmentPlanRepository.findByEntrustmentPlanId(entrustmentPlanIdsByEntrustmentId.get(e.getId())).map(ep -> ep.getSemester().getId()).orElseThrow(() -> new ResourceInternalServerError(Semester.class.getSimpleName()))))
				.collect(Collectors.toList());
	}

	@Transactional
	public EntrustmentResponseDTO createEntrustment(long semesterId, EntrustmentCreateRequestDTO entrustment) {
		User user = userRepository.findByEmail("anna.sekretarz@pwr.edu.pl")
				.orElseThrow(() -> new ResourceInternalServerError("User"));
		EntrustmentStatus initialEntrustmentStatus = entrustmentStatusRepository.findByCode(EntrustmentStatus.StatusCode.PROPOSED)
				.orElseThrow(() -> new ResourceInternalServerError("EntrustmentStatus"));
		CourseInstructor instructor = courseInstructorRepository.findById(entrustment.getCourseInstructorId())
				.orElseThrow(() -> new ResourceNotFoundException("Course Instructor", "id", String.valueOf(entrustment.getCourseInstructorId())));
		Course course = courseRepository.findByCode(entrustment.getCourseCode())
				.orElseThrow(() -> new ResourceNotFoundException("Course", "code", entrustment.getCourseCode()));
		EntrustmentPlan entrustmentPlan = entrustmentPlanRepository.findBySemesterId(semesterId)
				.orElseThrow(() -> new ResourceNotFoundException("EntrustmentPlan", "semester_id", String.valueOf(semesterId)));

		int firstVersion = 1;
		Entrustment entrustmentToCreate = Entrustment.builder()
				.lastVersion(firstVersion)
				.entrustmentPlan(entrustmentPlan)
				.build();
		Entrustment entrustmentCreated = entrustmentRepository.save(entrustmentToCreate);
		VEntrustment ventrustmentToCreate = VEntrustment.builder()
				.id(new VEntrustmentId(entrustmentCreated, entrustmentCreated.getLastVersion()))
				.course(course)
				.numberOfHours(entrustment.getNumberOfHours())
				.courseInstructor(instructor)
				.entrustmentStatus(initialEntrustmentStatus)
				.build();
		VEntrustment ventrustmentCreated = ventrustmentRepository.save(ventrustmentToCreate);
		EntrustmentLog entrustmentLog = EntrustmentLog.builder()
				.entrustment(entrustmentCreated)
				.entrustmentVersion(firstVersion)
				.user(user)
				.changeType(EntrustmentLog.ChangeType.I)
				.build();
		entrustmentLogRepository.save(entrustmentLog);

		long entrustmentPlanId = ventrustmentCreated.getId().getEntrustment().getEntrustmentPlan().getId();
		return complexModelMapper.map(ventrustmentCreated, EntrustmentResponseDTO.class)
				.setSemesterId(entrustmentPlanRepository.findByEntrustmentPlanId(entrustmentPlanId).orElseThrow(() -> new ResourceInternalServerError(Semester.class.getSimpleName())).getId());
	}

	@Transactional
	public EntrustmentResponseDTO modifyEntrustment(Long semesterId, Long entrustmentId, EntrustmentModifyRequestDTO entrustmentBody) {
		User user = userRepository.findByEmail("jan.kowalski@pwr.edu.pl")
				.orElseThrow(() -> new ResourceInternalServerError(User.class.getSimpleName()));
		EntrustmentStatus modifiedEntrustmentStatus = entrustmentStatusRepository.findByCode(EntrustmentStatus.StatusCode.MODIFIED)
				.orElseThrow(() -> new ResourceInternalServerError(EntrustmentStatus.class.getSimpleName()));

		Entrustment entrustment = entrustmentRepository.findById(entrustmentId)
				.map(e -> e.setLastVersion(e.getLastVersion() + 1))
				.orElseThrow(() -> new ResourceNotFoundException(Entrustment.class.getName(), "id", String.valueOf(entrustmentId)));
		VEntrustment ventrustment = ventrustmentRepository.findByIdEntrustmentIdAndIdVersion(entrustment.getId(), entrustment.getLastVersion() - 1)
				.map(ve -> VEntrustment.builder()
						.id(new VEntrustmentId(entrustment, entrustment.getLastVersion()))
						.numberOfHours(entrustmentBody.getNumberOfHours())
						.course(ve.getCourse())
						.courseInstructor(ve.getCourseInstructor())
						.entrustmentStatus(modifiedEntrustmentStatus)
						.build()
				)
				.orElseThrow(() -> new ResourceNotFoundException(VEntrustment.class.getName(), "id", String.valueOf(entrustmentId)));
		entrustmentRepository.save(entrustment);
		VEntrustment ventrustmentUpdated = ventrustmentRepository.save(ventrustment);

		EntrustmentLog entrustmentLog = EntrustmentLog.builder()
				.entrustment(entrustment)
				.entrustmentVersion(entrustment.getLastVersion())
				.user(user)
				.changeType(EntrustmentLog.ChangeType.U)
				.build();
		entrustmentLogRepository.save(entrustmentLog);

		long entrustmentPlanId = ventrustmentUpdated.getId().getEntrustment().getEntrustmentPlan().getId();
		return complexModelMapper.map(ventrustmentUpdated, EntrustmentResponseDTO.class)
				.setSemesterId(entrustmentPlanRepository.findByEntrustmentPlanId(entrustmentPlanId).orElseThrow(() -> new ResourceInternalServerError(Semester.class.getSimpleName())).getId());
	}

	@Transactional
	public EntrustmentResponseDTO acceptEntrustment(Long semesterId, Long entrustmentId) {
		EntrustmentStatus acceptedEntrustmentStatus = entrustmentStatusRepository.findByCode(EntrustmentStatus.StatusCode.ACCEPTED)
				.orElseThrow(() -> new ResourceInternalServerError(EntrustmentStatus.class.getSimpleName()));

		return changeEntrustmentStatus(entrustmentId, acceptedEntrustmentStatus);
	}

	@Transactional
	public EntrustmentResponseDTO rejectEntrustment(Long semesterId, Long entrustmentId) {
		EntrustmentStatus acceptedEntrustmentStatus = entrustmentStatusRepository.findByCode(EntrustmentStatus.StatusCode.REJECTED)
				.orElseThrow(() -> new ResourceInternalServerError(EntrustmentStatus.class.getSimpleName()));

		return changeEntrustmentStatus(entrustmentId, acceptedEntrustmentStatus);
	}

	private EntrustmentResponseDTO changeEntrustmentStatus(long entrustmentId, EntrustmentStatus acceptedEntrustmentStatus) {
		User user = userRepository.findByEmail("jan.kowalski@pwr.edu.pl")
				.orElseThrow(() -> new ResourceInternalServerError(User.class.getSimpleName()));
		Entrustment entrustment = entrustmentRepository.findById(entrustmentId)
				.map(e -> e.setLastVersion(e.getLastVersion() + 1))
				.orElseThrow(() -> new ResourceNotFoundException(Entrustment.class.getName(), "id", String.valueOf(entrustmentId)));
		VEntrustment ventrustment = ventrustmentRepository.findByIdEntrustmentIdAndIdVersion(entrustment.getId(), entrustment.getLastVersion() - 1)
				.map(ve -> VEntrustment.builder()
						.id(new VEntrustmentId(entrustment, entrustment.getLastVersion()))
						.numberOfHours(ve.getNumberOfHours())
						.course(ve.getCourse())
						.courseInstructor(ve.getCourseInstructor())
						.entrustmentStatus(acceptedEntrustmentStatus)
						.build()
				)
				.orElseThrow(() -> new ResourceNotFoundException(VEntrustment.class.getName(), "id", String.valueOf(entrustmentId)));
		entrustmentRepository.save(entrustment);
		VEntrustment ventrustmentUpdated = ventrustmentRepository.save(ventrustment);

		EntrustmentLog entrustmentLog = EntrustmentLog.builder()
				.entrustment(entrustment)
				.entrustmentVersion(entrustment.getLastVersion())
				.user(user)
				.changeType(EntrustmentLog.ChangeType.U)
				.build();
		entrustmentLogRepository.save(entrustmentLog);

		long entrustmentPlanId = ventrustmentUpdated.getId().getEntrustment().getEntrustmentPlan().getId();
		return complexModelMapper.map(ventrustmentUpdated, EntrustmentResponseDTO.class)
				.setSemesterId(entrustmentPlanRepository.findByEntrustmentPlanId(entrustmentPlanId).orElseThrow(() -> new ResourceInternalServerError(Semester.class.getSimpleName())).getId());
	}

	public int findSumOfEntrustmentsHoursForCourseInstructor(Long courseInstructorId) {
		return ventrustmentRepository.calculateSumOfEntrustedHoursForCourseInstructor(courseInstructorId);
	}

	public int findRemainingHoursToEntrustForSemesterAndCourse(Long semesterId, Long courseId) {
		EntrustmentPlan entrustmentPlan = entrustmentPlanRepository.findBySemesterId(semesterId)
				.orElseThrow(() -> new ResourceNotFoundException("EntrustmentPlan", "semester_id", String.valueOf(semesterId)));
		List<VEntrustment> entrustments = ventrustmentRepository.findAllActualByCourseAndEntrustmentPlan(courseId, entrustmentPlan.getId());
		if (entrustments.size() == 0) {
			return 0;
		}
		Course course = entrustments.get(0).getCourse();
		int entrustedHours = entrustments.stream().mapToInt(VEntrustment::getNumberOfHours).sum();
		return course.getZzuHours() * entrustmentPlan.getNumberOfStudents() / course.getStudentsPerGroup() - entrustedHours;
	}
}
