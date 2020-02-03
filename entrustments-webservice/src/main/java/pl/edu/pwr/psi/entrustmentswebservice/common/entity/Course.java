package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "Courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@NaturalId
	@Column(updatable = false, nullable = false)
	private String code;

	@Column(updatable = false, nullable = false)
	private String name;

	@Column(name = "zzu_hours", updatable = false, nullable = false)
	private int zzuHours;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_didactic_form", referencedColumnName = "id", nullable = false)
	private DidacticForm didacticForm;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module", referencedColumnName = "id", nullable = false)
	private Module module;
}
