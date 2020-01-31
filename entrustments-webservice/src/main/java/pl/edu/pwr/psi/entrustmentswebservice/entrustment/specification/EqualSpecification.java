package pl.edu.pwr.psi.entrustmentswebservice.entrustment.specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class EqualSpecification<T> implements Specification<T> {

	private String key;
	private String value;

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
		return value == null ? cb.isTrue(cb.literal(true)) : cb.equal(root.get(key), value);
	}
}
