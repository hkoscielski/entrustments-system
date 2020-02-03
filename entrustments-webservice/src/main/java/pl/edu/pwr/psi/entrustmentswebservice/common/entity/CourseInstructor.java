package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
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

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_course_instructor")
	private Set<Agreement> agreements;

	@ManyToMany
	@JoinTable(name = "Instructors_Didactic_Forms",
			joinColumns = {@JoinColumn(name = "id_course_instructor", nullable = false)},
			inverseJoinColumns = {@JoinColumn(name = "id_didactic_form", nullable = false)}
	)
	private Set<DidacticForm> didacticForms = new HashSet<>();

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
}
