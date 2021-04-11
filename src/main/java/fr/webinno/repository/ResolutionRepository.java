package fr.webinno.repository;

import fr.webinno.domain.Resolution;
import fr.webinno.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long>{

}
