package fr.webinno.repository;

import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserResolutionRepository extends JpaRepository<UserResolution, Long> {
    List<UserResolution> findAllByUser(User User);
}
