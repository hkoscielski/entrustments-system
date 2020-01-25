package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Teachers")
@PrimaryKeyJoinColumn(name = "id_course_instructor", referencedColumnName = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends CourseInstructor {

	@Column(nullable = false)
	private int pensum;

	@Column(name = "time_basis", nullable = false)
	private int timeBasis;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_position", referencedColumnName = "id", nullable = false)
	private Position position;
}
