package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Course_Instructors")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInstructor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String surname;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_academic_degree", referencedColumnName = "id", nullable = false)
	private AcademicDegree academicDegree;

	@ManyToMany
	@JoinTable(name = "Instructors_Didactic_Forms",
			joinColumns = {@JoinColumn(name = "id_course_instructor", nullable = false)},
			inverseJoinColumns = {@JoinColumn(name = "id_didactic_form", nullable = false)}
	)
	private Set<DidacticForm> didacticForms = new HashSet<>();
}
