package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "Courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@NaturalId
	@Column(updatable = false, nullable = false)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(name = "zzu_hours", nullable = false)
	private int zzuHours;

	@Column(name = "students_per_group", nullable = false)
	private int studentsPerGroup;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_didactic_form", referencedColumnName = "id", nullable = false)
	private DidacticForm didacticForm;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module", referencedColumnName = "id", nullable = false)
	private Module module;
}
