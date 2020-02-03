package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Fields_Of_Study")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldOfStudy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(updatable = false, nullable = false)
	private String name;

	@Column(name = "short_name", updatable = false, nullable = false)
	private String shortName;
}
