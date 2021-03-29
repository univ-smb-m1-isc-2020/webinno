package fr.webinno.service;

import fr.webinno.domain.Resolution;
import fr.webinno.repository.ResolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ResolutionServiceImpl implements ResolutionService {
    ResolutionRepository resolutionRepository;

    @Autowired
    public ResolutionServiceImpl(ResolutionRepository resolutionRepository){
        this.resolutionRepository = resolutionRepository;
    }

    @Override
    public void addResolution(Resolution resolution) {
        resolutionRepository.save(resolution);
    }

    @Override
    public List<Resolution> getAllResolutions() {
        List<Resolution> resolutionList = resolutionRepository.findAll();
        return resolutionList;
    }

    @PostConstruct
    public void initialize(){
        if(resolutionRepository.findAll().isEmpty()){
            resolutionRepository.saveAndFlush(new Resolution("Me lever à 8h","2 mois",0));
            resolutionRepository.saveAndFlush(new Resolution("Me coucher avant 5h","4 semaine",0));
        }
    }
}
