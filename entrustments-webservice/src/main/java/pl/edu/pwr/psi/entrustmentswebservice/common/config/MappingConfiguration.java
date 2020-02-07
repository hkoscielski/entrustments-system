package pl.edu.pwr.psi.entrustmentswebservice.common.config;

import org.modelmapper.Converter;
import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Agreement;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Course;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.CourseInstructor;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.DoctoralStudent;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.FieldOfStudy;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.FormOfStudy;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Module;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Semester;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Specialist;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Specialty;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLanguage;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.StudyLevel;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Teacher;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.CourseInstructorResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.CourseResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.FieldOfStudyResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.common.payload.response.SemesterResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.VEntrustment;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.EntrustmentResponseDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MappingConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DoctoralStudent.class, CourseInstructorResponseDTO.class)
				.addMappings(doctoralStudentToDTOMapping());
		modelMapper.createTypeMap(Specialist.class, CourseInstructorResponseDTO.class)
				.addMappings(specialistToDTOMapping());
		modelMapper.createTypeMap(Teacher.class, CourseInstructorResponseDTO.class)
				.addMappings(teacherToDTOMapping());
		modelMapper.createTypeMap(Semester.class, SemesterResponseDTO.class)
				.addMappings(semesterToDTOMapping());
		modelMapper.createTypeMap(VEntrustment.class, EntrustmentResponseDTO.class)
				.addMappings(ventrustmentToDTOMapping());
		return modelMapper;
	}

	@Bean
	@DependsOn("modelMapper")
	public ComplexModelMapper complexModelMapper() {
		return new ComplexModelMapper();
	}

	private ExpressionMap<DoctoralStudent, CourseInstructorResponseDTO> doctoralStudentToDTOMapping() {
		Converter<CourseInstructor, String> courseInstructorTypeConverter =
				getCourseInstructorTypeConverter();
		Converter<DoctoralStudent, Map<String, String>> additionalAttrConverter =
				mappingContext -> {
					Map<String, String> additionalAttributes = new HashMap<>();
					additionalAttributes.put("pensum", String.valueOf(mappingContext.getSource().getPensum()));
					return additionalAttributes;
				};
		Converter<Set<Agreement>, Set<CourseInstructorResponseDTO.AgreementResponseDTO>> agreementsToDTOConverter = getSetAgreementToDTOConverter();

		return mapping -> {
			mapping.map(sg -> sg.getAcademicDegree().getShortName(), CourseInstructorResponseDTO::setAcademicDegree);
			mapping.using(agreementsToDTOConverter).map(CourseInstructor::getAgreements, CourseInstructorResponseDTO::setAgreements);
			mapping.using(courseInstructorTypeConverter).map(sg -> sg, CourseInstructorResponseDTO::setCourseInstructorType);
			mapping.using(additionalAttrConverter).map(sg -> sg, CourseInstructorResponseDTO::setAdditionalAttributes);
		};
	}

	private ExpressionMap<Specialist, CourseInstructorResponseDTO> specialistToDTOMapping() {
		Converter<CourseInstructor, String> courseInstructorTypeConverter =
				getCourseInstructorTypeConverter();
		Converter<DoctoralStudent, Map<String, String>> additionalAttrConverter =
				mappingContext -> new HashMap<>();
		Converter<Set<Agreement>, Set<CourseInstructorResponseDTO.AgreementResponseDTO>> agreementsToDTOConverter = getSetAgreementToDTOConverter();

		return mapping -> {
			mapping.map(sg -> sg.getAcademicDegree().getShortName(), CourseInstructorResponseDTO::setAcademicDegree);
			mapping.using(agreementsToDTOConverter).map(CourseInstructor::getAgreements, CourseInstructorResponseDTO::setAgreements);
			mapping.using(courseInstructorTypeConverter).map(sg -> sg, CourseInstructorResponseDTO::setCourseInstructorType);
			mapping.using(additionalAttrConverter).map(sg -> sg, CourseInstructorResponseDTO::setAdditionalAttributes);
		};
	}

	private ExpressionMap<Teacher, CourseInstructorResponseDTO> teacherToDTOMapping() {
		Converter<CourseInstructor, String> courseInstructorTypeConverter =
				getCourseInstructorTypeConverter();
		Converter<Teacher, Map<String, String>> additionalAttrConverter =
				mappingContext -> {
					Map<String, String> additionalAttributes = new HashMap<>();
					additionalAttributes.put("pensum", String.valueOf(mappingContext.getSource().getPensum()));
					additionalAttributes.put("timeBasis", String.valueOf(mappingContext.getSource().getTimeBasis()));
					additionalAttributes.put("position", String.valueOf(mappingContext.getSource().getPosition().getName()));
					return additionalAttributes;
				};
		Converter<Set<Agreement>, Set<CourseInstructorResponseDTO.AgreementResponseDTO>> agreementsToDTOConverter = getSetAgreementToDTOConverter();

		return mapping -> {
			mapping.map(sg -> sg.getAcademicDegree().getShortName(), CourseInstructorResponseDTO::setAcademicDegree);
			mapping.using(agreementsToDTOConverter).map(CourseInstructor::getAgreements, CourseInstructorResponseDTO::setAgreements);
			mapping.using(courseInstructorTypeConverter).map(sg -> sg, CourseInstructorResponseDTO::setCourseInstructorType);
			mapping.using(additionalAttrConverter).map(sg -> sg, CourseInstructorResponseDTO::setAdditionalAttributes);
		};
	}

	private Converter<CourseInstructor, String> getCourseInstructorTypeConverter() {
		return mappingContext -> mappingContext.getSource().getClass().getSimpleName();
	}

	private Converter<Set<Agreement>, Set<CourseInstructorResponseDTO.AgreementResponseDTO>> getSetAgreementToDTOConverter() {
		return mappingContext -> mappingContext.getSource().stream().map(a ->
				new CourseInstructorResponseDTO.AgreementResponseDTO(
						a.getStartDate(),
						a.getEndDate(),
						a.getDidacticForm().getCode()
				)
		).collect(Collectors.toSet());
	}

	private ExpressionMap<Semester, SemesterResponseDTO> semesterToDTOMapping() {
		Converter<FieldOfStudy, FieldOfStudyResponseDTO> fieldOfStudyConverter =
				mappingContext -> new FieldOfStudyResponseDTO(mappingContext.getSource().getName(), mappingContext.getSource().getShortName());
		Converter<Specialty, SemesterResponseDTO.SpecialtyResponseDTO> specialtyConverter =
				mappingContext -> mappingContext.getSource() != null ? new SemesterResponseDTO.SpecialtyResponseDTO(mappingContext.getSource().getName(), mappingContext.getSource().getShortName()) : null;
		Converter<StudyLevel, SemesterResponseDTO.StudyLevelResponseDTO> studyLevelConverter =
				mappingContext -> new SemesterResponseDTO.StudyLevelResponseDTO(mappingContext.getSource().getCode().name(), mappingContext.getSource().getName());
		Converter<FormOfStudy, SemesterResponseDTO.FormOfStudyResponseDTO> formOfStudyConverter =
				mappingContext -> new SemesterResponseDTO.FormOfStudyResponseDTO(mappingContext.getSource().getCode().name(), mappingContext.getSource().getName());
		Converter<StudyLanguage, SemesterResponseDTO.StudyLanguageResponseDTO> studyLanguageConverter =
				mappingContext -> new SemesterResponseDTO.StudyLanguageResponseDTO(mappingContext.getSource().getCode().name(), mappingContext.getSource().getName());
		Converter<Set<Course>, Set<SemesterResponseDTO.CourseResponseDTO>> courseConverter =
				mappingContext -> mappingContext.getSource().stream()
						.map(c -> new SemesterResponseDTO.CourseResponseDTO(
								c.getId(),
								c.getCode(),
								c.getName(),
								c.getZzuHours(),
								new SemesterResponseDTO.DidacticFormResponseDTO(
										c.getDidacticForm().getCode(),
										c.getDidacticForm().getName()
								)
						)).collect(Collectors.toSet());
		Converter<Set<Module>, Set<SemesterResponseDTO.ModuleResponseDTO>> moduleConverter =
				mappingContext -> mappingContext.getSource().stream()
						.map(m -> new SemesterResponseDTO.ModuleResponseDTO(
								m.getCode(),
								m.getName(),
								m.getCourses().stream()
										.map(c -> new SemesterResponseDTO.CourseResponseDTO(
										c.getId(),
										c.getCode(),
										c.getName(),
										c.getZzuHours(),
										new SemesterResponseDTO.DidacticFormResponseDTO(
												c.getDidacticForm().getCode(),
												c.getDidacticForm().getName()
										)
								)).collect(Collectors.toSet())
						)).collect(Collectors.toSet());

		return mapping -> {
			mapping.map(sg -> sg.getSemesterName().getName(), SemesterResponseDTO::setSemesterName);
			mapping.map(sg -> sg.getStudyPlan().getStartAcademicYear(), SemesterResponseDTO::setStartAcademicYear);
			mapping.map(sg -> sg.getStudyPlan().getStartSemesterName().getName(), SemesterResponseDTO::setStartSemesterName);
			mapping.using(fieldOfStudyConverter).map(sg -> sg.getStudyPlan().getFieldOfStudy(), SemesterResponseDTO::setFieldOfStudy);
			mapping.using(specialtyConverter).map(sg -> sg.getStudyPlan().getSpecialty(), SemesterResponseDTO::setSpecialty);
			mapping.using(studyLevelConverter).map(sg -> sg.getStudyPlan().getStudyLevel(), SemesterResponseDTO::setStudyLevel);
			mapping.using(formOfStudyConverter).map(sg -> sg.getStudyPlan().getFormOfStudy(), SemesterResponseDTO::setFormOfStudy);
			mapping.using(studyLanguageConverter).map(sg -> sg.getStudyPlan().getStudyLanguage(), SemesterResponseDTO::setStudyLanguage);
			mapping.using(courseConverter).map(Semester::getCourses, SemesterResponseDTO::setCourses);
			mapping.using(moduleConverter).map(Semester::getModules, SemesterResponseDTO::setModules);
		};
	}

	private ExpressionMap<VEntrustment, EntrustmentResponseDTO> ventrustmentToDTOMapping() {
		Converter<Course, CourseResponseDTO> courseConverter =
				mappingContext -> new CourseResponseDTO(
						mappingContext.getSource().getCode(),
						mappingContext.getSource().getName()
				);
		Converter<CourseInstructor, EntrustmentResponseDTO.CourseInstructorResponseDTO> courseInstructorConverter =
				mappingContext -> new EntrustmentResponseDTO.CourseInstructorResponseDTO(
						mappingContext.getSource().getId(),
						mappingContext.getSource().getFirstName(),
						mappingContext.getSource().getSurname(),
						mappingContext.getSource().getAcademicDegree().getShortName()
				);

		return mapping -> {
			mapping.map(sg -> sg.getId().getEntrustment().getId(), EntrustmentResponseDTO::setId);
			mapping.using(courseConverter).map(VEntrustment::getCourse, EntrustmentResponseDTO::setCourse);
			mapping.using(courseInstructorConverter).map(VEntrustment::getCourseInstructor, EntrustmentResponseDTO::setCourseInstructor);
			mapping.map(sg -> sg.getEntrustmentStatus().getName(), EntrustmentResponseDTO::setStatus);
		};
	}
}
