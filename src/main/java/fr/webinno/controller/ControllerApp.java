package fr.webinno.controller;

import fr.webinno.domain.Historique;
import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;
import fr.webinno.form.*;
import fr.webinno.service.HistoriqueService;
import fr.webinno.service.ResolutionService;
import fr.webinno.service.UserResolutionService;
import fr.webinno.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerApp {
    private final ResolutionService resolutionService;
    private final UserService userService;
    private final UserResolutionService userResolutionService;
    private final HistoriqueService historiqueService;

    @Autowired
    public ControllerApp(ResolutionService resolutionService, UserService userService, UserResolutionService userResolutionService, HistoriqueService historiqueService) {
        this.resolutionService = resolutionService;
        this.userService = userService;
        this.userResolutionService = userResolutionService;
        this.historiqueService = historiqueService;
    }

    @GetMapping("*")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute(new LoginForm());
        return "login";
    }


    @PostMapping("/tryLogin")
    public String tryLogin(@ModelAttribute LoginForm loginForm, Model model){
        // 1 - Récupération donnée du formulaire
        var user = userService.getUserByName(loginForm.getUserName());
        var password = loginForm.getPassword();

        if(user == null){
            return index(model);
        }

        // 2 - Vérification pour la connexion
        if(user.login(password)){
            // 3 - Redirection
            var resolutions = resolutionService.getAllResolutions();
            model.addAttribute("resolutions", resolutions);
            return user(model, user);
        }
        else{
            return index(model);
        }
    }


    public String user(Model model, User user){
        model.addAttribute("user", user);

        var user_resolutions = userResolutionService.getAllUserResolutionByUser(user);
        model.addAttribute("user_resolutions", user_resolutions);

        model.addAttribute("userResolutionForm", new UserResolutionForm());
        return "user";
    }

    /*
        Quand un utilisateur veut accéder au détail d'une de ces résolutions
     */
    @PostMapping("/selectUserResolution")
    public String selectUserResolution(@ModelAttribute UserResolutionForm userResolutionForm, Model model){
        //Récupération form
        var user = userService.getUserById(userResolutionForm.getIdUser());
        var resolution = resolutionService.getById(userResolutionForm.getIdResolution());

        //Récupération BDD
        var user_resolution = userResolutionService.getByUserAndResolution(user.get(), resolution.get());

        //Récupération de l'historique de la résolution sur la derniere semaine
        ArrayList<Historique> histLastSemaine = user_resolution.getHistoriqueLastSemaine();

        for(int i=0; i<histLastSemaine.size(); i++){
            if(histLastSemaine.get(i).getIdHistorique() == 0){
                historiqueService.addHistorique(histLastSemaine.get(i));
            }
        }

        //Envoie page
        model.addAttribute("user", user.get());
        model.addAttribute("histLastSemaine", histLastSemaine);
        model.addAttribute("user_resolution", user_resolution);
        model.addAttribute("resolutionValidationForm", new ResolutionValidationForm());

        return "resolution";
    }



    @PostMapping("/addUserResolutionForm")
    public String addUserResolutionForm(@ModelAttribute SelectResolutionForm selectResolutionForm, Model model){
        //1 - Récupération de la résolution
        var resolution = resolutionService.getById(selectResolutionForm.getIdResolution());
        if(!resolution.isPresent()){
            return "login";
        }

        //2 - Récupération de l'user
        var user = userService.getUserById(selectResolutionForm.getIdUser());
        if(!user.isPresent()){
            return "login";
        }

        //3 - Envoi data
        model.addAttribute("resolution", resolution.get());
        model.addAttribute("user", user.get());
        model.addAttribute("addUserResolutionForm", new AddUserResolutionForm());

        return "addUserResolutionForm";
    }

    @PostMapping("/updateValidation")
    public String updateValidationUserResolution(@ModelAttribute ResolutionValidationForm resolutionValidationForm, Model model){
        //Récupération données du formulaire
        var user = userService.getUserById(resolutionValidationForm.getIdUser());
        var historic = historiqueService.getById(resolutionValidationForm.getIdHistorique()).get();
        var user_resolution = historic.getUserResolution();

        historic.setDone(!historic.isDone());

        historiqueService.updateHistorique(historic);

        //Récupération de l'historique de la résolution sur la derniere semaine
        ArrayList<Historique> histLastSemaine = user_resolution.getHistoriqueLastSemaine();

        for(int i=0; i<histLastSemaine.size(); i++){
            if(histLastSemaine.get(i).getIdHistorique() == 0){
                historiqueService.addHistorique(histLastSemaine.get(i));
            }
        }

        //Envoie page
        model.addAttribute("user", user.get());
        model.addAttribute("histLastSemaine", histLastSemaine);
        model.addAttribute("user_resolution", user_resolution);
        model.addAttribute("resolutionValidationForm", new ResolutionValidationForm());

        return "resolution";

    }
}
