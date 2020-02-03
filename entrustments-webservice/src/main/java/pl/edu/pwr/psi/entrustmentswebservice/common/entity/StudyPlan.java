package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Study_Plans")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudyPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_field_of_study", referencedColumnName = "id", nullable = false)
	private FieldOfStudy fieldOfStudy;

	@ManyToOne
	@JoinColumn(name = "id_specialty", referencedColumnName = "id")
	private Specialty specialty;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_study_level", referencedColumnName = "id", nullable = false)
	private StudyLevel studyLevel;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_form_of_studies", referencedColumnName = "id", nullable = false)
	private FormOfStudy formOfStudy;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_study_language", referencedColumnName = "id", nullable = false)
	private StudyLanguage studyLanguage;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_start_semester_name", referencedColumnName = "id", nullable = false)
	private SemesterName startSemesterName;

	@Column(name = "start_academic_year", updatable = false, nullable = false)
	private String startAcademicYear;
}
