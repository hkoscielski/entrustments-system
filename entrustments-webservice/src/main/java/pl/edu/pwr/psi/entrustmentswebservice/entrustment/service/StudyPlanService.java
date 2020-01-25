package pl.edu.pwr.psi.entrustmentswebservice.entrustment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.Faculty;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.FacultyResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository.FacultyRepository;

import java.util.List;

@Service
public class StudyPlanService {

	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private ComplexModelMapper complexModelMapper;

	public List<FacultyResponseDTO> findAllFaculties() {
		List<Faculty> faculties = facultyRepository.findAll();
		return complexModelMapper.mapAll(faculties, FacultyResponseDTO.class);
	}
}
