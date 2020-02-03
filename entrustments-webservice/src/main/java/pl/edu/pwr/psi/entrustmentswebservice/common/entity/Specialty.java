package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "Specialties")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class Specialty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "short_name", nullable = false)
	private String shortName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_field_of_study", referencedColumnName = "id", nullable = false)
	private FieldOfStudy fieldOfStudy;
}
