package pl.edu.pwr.psi.entrustmentswebservice.common.config;

import org.modelmapper.Converter;
import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.edu.pwr.psi.entrustmentswebservice.common.mapping.ComplexModelMapper;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.CourseInstructor;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.DoctoralStudent;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.Specialist;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.Teacher;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.payload.response.CourseInstructorResponseDTO;

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
}
