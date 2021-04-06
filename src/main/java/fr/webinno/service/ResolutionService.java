package fr.webinno.service;

import fr.webinno.domain.Resolution;

import java.util.List;

public interface ResolutionService {
    void addResolution(Resolution resolution);
    List<Resolution> getAllResolutions();
}
