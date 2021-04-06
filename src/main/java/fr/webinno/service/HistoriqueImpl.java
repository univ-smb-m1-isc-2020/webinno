package fr.webinno.service;

import fr.webinno.repository.HistoriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriqueImpl implements HistoriqueService{
    HistoriqueRepository historiqueRepository;

    @Autowired
    public HistoriqueImpl(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }
}
