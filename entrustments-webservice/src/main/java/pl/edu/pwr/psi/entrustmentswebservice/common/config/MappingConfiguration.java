package pl.edu.pwr.psi.entrustmentswebservice.common.config;

import org.modelmapper.Converter;
import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.*;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.CourseInstructorResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.FieldOfStudyResponseDTO;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.SemesterResponseDTO;

import java.util.HashMap;
import java.util.Map;

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

		return mapping -> {
			mapping.map(sg -> sg.getAcademicDegree().getName(), CourseInstructorResponseDTO::setAcademicDegree);
			mapping.using(courseInstructorTypeConverter).map(sg -> sg, CourseInstructorResponseDTO::setCourseInstructorType);
			mapping.using(additionalAttrConverter).map(sg -> sg, CourseInstructorResponseDTO::setAdditionalAttributes);
		};
	}

	private ExpressionMap<Specialist, CourseInstructorResponseDTO> specialistToDTOMapping() {
		Converter<CourseInstructor, String> courseInstructorTypeConverter =
				getCourseInstructorTypeConverter();
		Converter<DoctoralStudent, Map<String, String>> additionalAttrConverter =
				mappingContext -> new HashMap<>();

		return mapping -> {
			mapping.map(sg -> sg.getAcademicDegree().getName(), CourseInstructorResponseDTO::setAcademicDegree);
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

		return mapping -> {
			mapping.map(sg -> sg.getAcademicDegree().getName(), CourseInstructorResponseDTO::setAcademicDegree);
			mapping.using(courseInstructorTypeConverter).map(sg -> sg, CourseInstructorResponseDTO::setCourseInstructorType);
			mapping.using(additionalAttrConverter).map(sg -> sg, CourseInstructorResponseDTO::setAdditionalAttributes);
		};
	}

	private Converter<CourseInstructor, String> getCourseInstructorTypeConverter() {
		return mappingContext -> mappingContext.getSource().getClass().getSimpleName();
	}


	private ExpressionMap<Semester, SemesterResponseDTO> semesterToDTOMapping() {
		Converter<FieldOfStudy, FieldOfStudyResponseDTO> fieldOfStudyConverter =
				mappingContext -> new FieldOfStudyResponseDTO(mappingContext.getSource().getName(), mappingContext.getSource().getShortName());

		return mapping -> {
			mapping.map(sg -> sg.getSemesterName().getName(), SemesterResponseDTO::setSemesterName);
			mapping.map(sg -> sg.getStudyPlan().getStartAcademicYear(), SemesterResponseDTO::setStartAcademicYear);
			mapping.map(sg -> sg.getStudyPlan().getStartSemesterName().getName(), SemesterResponseDTO::setStartSemesterName);
			mapping.using(fieldOfStudyConverter).map(sg -> sg.getStudyPlan().getFieldOfStudy(), SemesterResponseDTO::setFieldOfStudy);
			mapping.map(sg -> sg.getStudyPlan().getSpecialty().getName(), SemesterResponseDTO::setSpecialty);
			mapping.map(sg -> sg.getStudyPlan().getStudyLevel().getName(), SemesterResponseDTO::setStudyLevel);
			mapping.map(sg -> sg.getStudyPlan().getFormOfStudy().getName(), SemesterResponseDTO::setFormOfStudy);
			mapping.map(sg -> sg.getStudyPlan().getStudyLanguage().getName(), SemesterResponseDTO::setStudyLanguage);
		};
	}
}
