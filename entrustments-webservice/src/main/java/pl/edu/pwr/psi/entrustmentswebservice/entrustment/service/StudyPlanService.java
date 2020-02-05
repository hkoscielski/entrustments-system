package pl.edu.pwr.psi.entrustmentswebservice.entrustment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.FieldOfStudy;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.FieldOfStudyDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.FieldOfStudyRepository;
import pl.edu.pwr.psi.entrustmentswebservice.common.util.DateUtil;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Faculty;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Semester;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.FacultyResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.SemesterResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.FacultyRepository;
import pl.edu.pwr.psi.entrustmentswebservice.common.repository.SemesterRepository;

import java.util.List;

@Service
public class StudyPlanService {

	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private FieldOfStudyRepository fieldOfStudyRepository;

	@Autowired
	private SemesterRepository semesterRepository;

	@Autowired
	private ComplexModelMapper complexModelMapper;

	public List<FacultyResponseDTO> findAllFaculties() {
		List<Faculty> faculties = facultyRepository.findAll();
		return complexModelMapper.mapAll(faculties, FacultyResponseDTO.class);
	}

	public List<FieldOfStudyDTO> findAllFieldsOfStudyForFaculty(String facultySymbol) {
		List<FieldOfStudy> fieldsOfStudy = fieldOfStudyRepository.findAllByFacultySymbol(facultySymbol);
		return complexModelMapper.mapAll(fieldsOfStudy, FieldOfStudyDTO.class);
	}

	public List<SemesterResponseDTO> findActualSemestersForFieldOfStudy(long fieldOfStudyId) {
		int currentStartAcademicYear = DateUtil.getCurrentStartAcademicYear();
		List<Semester> semesters = semesterRepository.findAllSemestersForFieldOfStudyAfterDate(fieldOfStudyId, currentStartAcademicYear);
		return complexModelMapper.mapAll(semesters, SemesterResponseDTO.class);
	}

	public List<SemesterResponseDTO> findActualSemesters() {
		int currentStartAcademicYear = DateUtil.getCurrentStartAcademicYear();
		List<Semester> semesters = semesterRepository.findAllSemestersAfterDate(currentStartAcademicYear);
		return complexModelMapper.mapAll(semesters, SemesterResponseDTO.class);
	}
}
