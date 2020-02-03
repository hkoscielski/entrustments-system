package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "Fields_Of_Study")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class FieldOfStudy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(updatable = false, nullable = false)
	private String name;

	@Column(name = "short_name", updatable = false, nullable = false)
	private String shortName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_faculty", referencedColumnName = "id", nullable = false)
	private Faculty faculty;
}
