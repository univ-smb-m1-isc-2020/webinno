package fr.webinno.service;

import fr.webinno.domain.Resolution;
import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;

import java.util.List;

public interface UserResolutionService {
    List<UserResolution> getAllUserResolutionByUser(User User);
    void addUserResolution(UserResolution userResolution);
    List<UserResolution> getAllUserResolutionByResolution(Resolution resolution);
}
