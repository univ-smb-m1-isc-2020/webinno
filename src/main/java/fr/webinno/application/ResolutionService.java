package fr.webinno.application;

import fr.webinno.infra.persistence.Resolution;
import fr.webinno.infra.persistence.ResolutionRepo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ResolutionService {
    private final ResolutionRepo repo;

    public ResolutionService(ResolutionRepo repo){
        this.repo = repo;
    }

    @PostConstruct
    public void initialize(){
        if(repo.findAll().isEmpty()){
            repo.saveAndFlush(new Resolution("Me lever à 8h","2 mois",0));
            repo.saveAndFlush(new Resolution("Me coucher avant 5h","4 semaine",0));
        }
    }

    public List<Resolution> resolutions(){
        return repo.findAll();
    }
}
