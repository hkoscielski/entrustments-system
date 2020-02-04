package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "Entrustments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class Entrustment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(name = "last_version", nullable = false)
	private int lastVersion;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_entrustment_plan", nullable = false)
	private EntrustmentPlan entrustmentPlan;
}
