package fr.webinno.service;

import fr.webinno.domain.Resolution;
import fr.webinno.repository.ResolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Resolution> getById(long idResolution) {
        return resolutionRepository.findById(idResolution);
    }

    @PostConstruct
    public void initialize(){
        if(resolutionRepository.findAll().isEmpty()){
            resolutionRepository.saveAndFlush(new Resolution("Me lever à 8h"));
            resolutionRepository.saveAndFlush(new Resolution("Me coucher avant 5h"));
        }
    }
}
