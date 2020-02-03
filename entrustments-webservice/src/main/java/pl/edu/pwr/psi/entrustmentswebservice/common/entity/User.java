package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@NaturalId
	@Column(unique = true, nullable = false)
	private String email;

	@NaturalId
	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "id_field_of_study", referencedColumnName = "id")
	private FieldOfStudy fieldOfStudy;
}
