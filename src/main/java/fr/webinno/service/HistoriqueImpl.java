package fr.webinno.service;

import fr.webinno.domain.Historique;
import fr.webinno.repository.HistoriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoriqueImpl implements HistoriqueService{
    HistoriqueRepository historiqueRepository;

    @Autowired
    public HistoriqueImpl(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Override
    public void addHistorique(Historique historique){
        historiqueRepository.saveAndFlush(historique);
    }

    @Override
    public Optional<Historique> getById(long id){
        return historiqueRepository.findById(id);
    }

    @Override
    public void updateHistorique(Historique hist){
        historiqueRepository.save(hist);
    }
}
