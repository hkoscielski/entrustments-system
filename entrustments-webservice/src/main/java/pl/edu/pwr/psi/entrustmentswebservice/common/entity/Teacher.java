package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.CourseInstructor;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Position;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Teachers")
@PrimaryKeyJoinColumn(name = "id_course_instructor", referencedColumnName = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Teacher extends CourseInstructor {

	@Column(nullable = false)
	private int pensum;

	@Column(name = "time_basis", nullable = false, precision = 3, scale = 2)
	private BigDecimal timeBasis;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_position", referencedColumnName = "id", nullable = false)
	private Position position;
}
