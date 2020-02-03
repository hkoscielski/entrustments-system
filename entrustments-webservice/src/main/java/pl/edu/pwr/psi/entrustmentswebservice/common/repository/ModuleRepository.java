package pl.edu.pwr.psi.entrustmentswebservice.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.common.entity.Module;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

	Optional<Module> findByCode(String code);
}
