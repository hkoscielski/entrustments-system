package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Semester;

import javax.persistence.*;

@Entity
@Table(name = "Entrustment_Plans")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntrustmentPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(name = "number_of_students", nullable = false)
	private int numberOfStudents;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_semester", nullable = false)
	private Semester semester;
}
