package fr.webinno.service;

import fr.webinno.domain.Resolution;

import java.util.List;
import java.util.Optional;

public interface ResolutionService {
    void addResolution(Resolution resolution);
    List<Resolution> getAllResolutions();
    Optional<Resolution> getById(long idResolution);
}
