package pl.edu.pwr.psi.entrustmentswebservice.entrustment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.CourseInstructor;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.CourseInstructorResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.CourseInstructorRepository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository.VEntrustmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseInstructorService {

	@Autowired
	private CourseInstructorRepository courseInstructorRepository;

	@Autowired
	private VEntrustmentRepository vEntrustmentRepository;

	@Autowired
	private ComplexModelMapper complexModelMapper;

	public List<CourseInstructorResponseDTO> findCourseInstructors() {
		List<CourseInstructor> courseInstructors = courseInstructorRepository.findAll();
		return complexModelMapper.mapAll(courseInstructors, CourseInstructorResponseDTO.class)
				.stream().map(ci -> ci.setEntrustedHours(vEntrustmentRepository.calculateSumOfEntrustedHoursForCourseInstructor(ci.getId())))
				.collect(Collectors.toList());
	}

	public List<CourseInstructorResponseDTO> findCourseInstructorsForFieldOfStudy(long fieldOfStudyId) {
		List<CourseInstructor> courseInstructors = courseInstructorRepository.findByIdFieldOfStudy(fieldOfStudyId);
		return complexModelMapper.mapAll(courseInstructors, CourseInstructorResponseDTO.class)
				.stream().map(ci -> ci.setEntrustedHours(vEntrustmentRepository.calculateSumOfEntrustedHoursForCourseInstructor(ci.getId())))
				.collect(Collectors.toList());
	}
}
