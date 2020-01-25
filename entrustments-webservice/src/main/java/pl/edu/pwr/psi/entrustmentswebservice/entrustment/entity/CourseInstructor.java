package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
}
