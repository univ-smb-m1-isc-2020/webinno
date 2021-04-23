package fr.webinno.repository;

import fr.webinno.domain.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Long> {


}
