package fr.webinno.controller;

import fr.webinno.application.ResolutionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
public class ResolutionController {
    private ResolutionService resolutionService;

    public ResolutionController(ResolutionService resolutionService){
        this.resolutionService = resolutionService;
    }

    @GetMapping(value="/resolutions")
    public List<String> resolutions(){
        return resolutionService.resolutions().stream().map(resolution -> resolution.getAction()).collect(toList());
    }

}
