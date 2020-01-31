package pl.edu.pwr.psi.entrustmentswebservice.entrustment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.VEntrustment;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentCriteriaDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository.EntrustmentRepository;

import java.util.List;

@Service
public class EntrustmentService {

	@Autowired
	private EntrustmentRepository entrustmentRepository;

	@Autowired
	private ComplexModelMapper complexModelMapper;

	public List<EntrustmentResponseDTO> findAllEntrustments(EntrustmentCriteriaDTO entrustmentCriteria) {
		List<VEntrustment> entrustments = entrustmentRepository.findAllEntrustmentsByPlanAndFilters(
				entrustmentCriteria.getAcademicYear(),
				entrustmentCriteria.getSemester(),
				entrustmentCriteria.getStudyLevel(),
				entrustmentCriteria.getSpecialty(),
				entrustmentCriteria.getCourseCode()
		);
		return complexModelMapper.mapAll(entrustments, EntrustmentResponseDTO.class);
	}
}
