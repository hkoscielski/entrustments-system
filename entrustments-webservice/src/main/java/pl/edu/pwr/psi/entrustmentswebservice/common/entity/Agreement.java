package pl.edu.pwr.psi.entrustmentswebservice.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Agreements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Agreement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(name = "agreement_start_date", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "agreement_end_date", nullable = false)
	private LocalDateTime endDate;

	@ManyToOne
	@JoinColumn(name = "id_didactic_form", referencedColumnName = "id", nullable = false)
	private DidacticForm didacticForm;
}
