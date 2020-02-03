package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.SemesterName;

import java.util.Optional;

import static pl.edu.pwr.psi.entrustmentswebservice.common.entity.SemesterName.SemesterType;

@Repository
public interface SemesterNameRepository extends JpaRepository<SemesterName, Long> {

	Optional<SemesterName> findByCode(SemesterType code);
}
