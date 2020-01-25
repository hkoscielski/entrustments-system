package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Doctoral_Students")
@PrimaryKeyJoinColumn(name = "id_course_instructor", referencedColumnName = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctoralStudent extends CourseInstructor {

	@Column(nullable = false)
	private int pensum;
}
