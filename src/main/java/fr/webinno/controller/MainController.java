package fr.webinno.controller;

import fr.webinno.domain.Resolution;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class MainController {
    private static List<Resolution> resolutions = new ArrayList<Resolution>();
    static {
        resolutions.add(new Resolution("Se coucher avant 20H", "3/mois", 2));
        resolutions.add(new Resolution("Se coucher avant 21H", "6/mois", 3));
        resolutions.add(new Resolution("Se coucher avant 22H", "15/an", 6));
        resolutions.add(new Resolution("Se coucher avant 23H", "1/jour", 1));
    }

    @GetMapping("/accueil")
    public String getResolutions(Model model){
        model.addAttribute("resolutions", resolutions);
        return "resolution";
    }
}