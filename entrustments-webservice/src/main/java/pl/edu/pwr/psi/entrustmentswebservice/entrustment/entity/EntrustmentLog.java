package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Entrustments_Logs")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EntrustmentLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_entrustment", referencedColumnName = "id", updatable = false, nullable = false)
	private Entrustment entrustment;

	@Column(name = "entrustment_version", updatable = false, nullable = false)
	private int entrustmentVersion;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", referencedColumnName = "id", updatable = false, nullable = false)
	private User user;

	@CreationTimestamp
	@Column(name = "change_date", updatable = false, nullable = false)
	private LocalDateTime changeDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "change_type", updatable = false, nullable = false)
	private ChangeType changeType;

	public enum ChangeType {
		I, U, D
	}
}
