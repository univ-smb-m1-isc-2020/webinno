package fr.webinno.service;

import fr.webinno.domain.Frequence;
import fr.webinno.domain.Resolution;
import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;
import fr.webinno.repository.ResolutionRepository;
import fr.webinno.repository.UserResolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            resolutionRepository.saveAndFlush(new Resolution("Me lever à 8h", Frequence.SEMAINE,3));
            resolutionRepository.saveAndFlush(new Resolution("Me coucher avant 5h", Frequence.JOUR,1));
        }
    }
}
