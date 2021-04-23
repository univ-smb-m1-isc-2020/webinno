package fr.webinno.service;

import fr.webinno.domain.Historique;

import java.util.Optional;

public interface HistoriqueService {
    void addHistorique(Historique historique);
    Optional<Historique> getById(long id);
    void updateHistorique(Historique hist);
}
