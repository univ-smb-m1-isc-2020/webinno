package fr.webinno.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolutionRepo extends JpaRepository<Resolution, Long>{
    Resolution findByName(String name);
}
