package pl.edu.pwr.psi.entrustmentswebservice.entrustment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.psi.entrustmentswebservice.common.exception.ResourceInternalServerError;
import pl.edu.pwr.psi.entrustmentswebservice.common.exception.ResourceNotFoundException;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.*;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.request.EntrustmentRequestDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository.*;

import java.util.List;

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
				entrustmentCriteria.getCourseCode()
		);
		return complexModelMapper.mapAll(entrustments, EntrustmentResponseDTO.class);
	}

	@Transactional
	public EntrustmentResponseDTO createEntrustment(long semesterId, EntrustmentRequestDTO entrustment) {
		User user = userRepository.findByEmail("anna.sekretarz@pwr.edu.pl")
				.orElseThrow(() -> new ResourceInternalServerError("User"));
		EntrustmentStatus initialEntrustmentStatus = entrustmentStatusRepository.findByName("Zaproponowane")
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
		return complexModelMapper.map(ventrustmentCreated, EntrustmentResponseDTO.class);
	}
}
