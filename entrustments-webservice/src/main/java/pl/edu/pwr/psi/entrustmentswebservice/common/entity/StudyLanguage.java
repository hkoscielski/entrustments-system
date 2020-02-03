package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Study_Languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudyLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(updatable = false, nullable = false)
	private LanguageCode code;

	@Column(unique = true, updatable = false, nullable = false)
	private String name;

	public enum LanguageCode {
		PL, EN
	}
}
