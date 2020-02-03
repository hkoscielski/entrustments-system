package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Academic_Degrees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcademicDegree {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(unique = true, updatable = false, nullable = false)
	private String name;

	@Column(name = "short_name", updatable = false, nullable = false)
	private String shortName;
}
