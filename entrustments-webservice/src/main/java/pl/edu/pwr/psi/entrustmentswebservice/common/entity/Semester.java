package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Semesters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Semester {

	@Id
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

	@ManyToMany
	@JoinTable(name = "Modules_Semesters",
			joinColumns = {@JoinColumn(name = "id_semester", nullable = false)},
			inverseJoinColumns = {@JoinColumn(name = "id_module")})
	private Set<Module> modules = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "Semesters_Courses",
			joinColumns = {@JoinColumn(name = "id_semester", nullable = false)},
			inverseJoinColumns = {@JoinColumn(name = "id_course", nullable = false)}
	)
	private Set<Course> courses = new HashSet<>();
}
