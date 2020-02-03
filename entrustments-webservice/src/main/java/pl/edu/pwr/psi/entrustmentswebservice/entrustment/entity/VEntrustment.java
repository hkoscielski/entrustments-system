package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.*;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Course;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.CourseInstructor;

import javax.persistence.*;

@Entity
@Table(name = "V_Entrustments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VEntrustment {

	@EmbeddedId
	private VEntrustmentId id;

	@Column(name = "number_of_hours", updatable = false, nullable = false)
	private int numberOfHours;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_course", referencedColumnName = "id", nullable = false)
	private Course course;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_course_instructor", referencedColumnName = "id", nullable = false)
	private CourseInstructor courseInstructor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_entrustment_status", referencedColumnName = "id", nullable = false)
	private EntrustmentStatus entrustmentStatus;
}
