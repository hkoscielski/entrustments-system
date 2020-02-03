package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Semesters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Semester {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(name = "academic_year", updatable = false, nullable = false)
	private String academicYear;

	@Column(name = "semester_number", updatable = false, nullable = false)
	private int semesterNumber;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_semester_name", referencedColumnName = "id", nullable = false)
	private SemesterName semesterName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_study_plan", referencedColumnName = "id", nullable = false)
	private StudyPlan studyPlan;
}
