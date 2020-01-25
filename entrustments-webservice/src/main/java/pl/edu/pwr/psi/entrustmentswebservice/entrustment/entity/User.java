package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
