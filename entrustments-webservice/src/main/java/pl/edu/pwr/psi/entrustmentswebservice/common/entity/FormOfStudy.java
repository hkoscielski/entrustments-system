package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Forms_Of_Studies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormOfStudy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(updatable = false, nullable = false)
	private FormType code;

	@Column(unique = true, updatable = false, nullable = false)
	private String name;

	public enum FormType {
		ST, NS
	}
}
