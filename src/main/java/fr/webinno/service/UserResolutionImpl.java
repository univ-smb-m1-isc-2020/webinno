package fr.webinno.service;

import fr.webinno.domain.Resolution;
import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;
import fr.webinno.repository.UserResolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResolutionImpl implements UserResolutionService{
    UserResolutionRepository userResolutionRepository;

    @Autowired
    public UserResolutionImpl(UserResolutionRepository userResolutionRepository) {
        this.userResolutionRepository = userResolutionRepository;
    }

    @Override
    public List<UserResolution> getAllUserResolutionByUser(User user) {
        return userResolutionRepository.findAllByUser(user);
    }

    @Override
    public void addUserResolution(UserResolution userResolution) {
        userResolutionRepository.save(userResolution);
    }

    @Override
    public UserResolution getByUserAndResolution(User user, Resolution resolution){
        return userResolutionRepository.findOneByUserAndResolution(user, resolution);
    }
}
