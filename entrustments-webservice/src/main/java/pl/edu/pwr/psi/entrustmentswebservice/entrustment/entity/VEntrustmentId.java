package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VEntrustmentId implements Serializable {

	@ManyToOne(optional = false)
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private Entrustment entrustment;

	@Column(updatable = false, nullable = false)
	private int version;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VEntrustmentId that = (VEntrustmentId) o;
		return version == that.version &&
				Objects.equals(entrustment, that.entrustment);
	}

	@Override
	public int hashCode() {
		return Objects.hash(entrustment, version);
	}
}
