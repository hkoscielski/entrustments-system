package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Entrustment_Statuses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntrustmentStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(updatable = false, nullable = false)
	private StatusCode code;

	@Column(unique = true, updatable = false, nullable = false)
	private String name;

	public enum StatusCode {
		PROPOSED, ACCEPTED, REJECTED, MODIFIED
	}
}
