package fr.webinno.controller;


import fr.webinno.domain.Resolution;
import fr.webinno.service.ResolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class ResolutionController {
    private final ResolutionService resolutionService;

    @Autowired
    public ResolutionController(ResolutionService resolutionService){
        this.resolutionService = resolutionService;
    }

    @GetMapping(value="/resolutions")
    public List<String> resolutions(){
        List<String> l = resolutionService.getAllResolutions().stream().map(resolution -> resolution.getAction()).collect(toList());
        return l;
    }

    @GetMapping("/showResolutions")
    public String getResolutions(Model model){
        var resolutions = resolutionService.getAllResolutions();
        model.addAttribute("resolutions", resolutions);

        return "resolution";
    }

}
