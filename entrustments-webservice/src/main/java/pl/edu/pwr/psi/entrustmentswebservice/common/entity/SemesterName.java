package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Semester_Names")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SemesterName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(updatable = false, nullable = false)
	private SemesterType code;

	@Column(unique = true, updatable = false, nullable = false)
	private String name;

	public enum SemesterType {
		SUMMER, WINTER
	}
}
