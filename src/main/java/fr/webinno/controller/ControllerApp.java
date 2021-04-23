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

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public String selectUserResolution(@ModelAttribute UserResolutionForm userResolutionForm, Model model) {
        //Récupération form
        var user = userService.getUserById(userResolutionForm.getIdUser());
        var resolution = resolutionService.getById(userResolutionForm.getIdResolution());

        //Récupération BDD
        var user_resolution = userResolutionService.getByUserAndResolution(user.get(), resolution.get());

        //Récupération de l'historique de la résolution sur la derniere semaine
        ArrayList<Historique> histLastSemaine = user_resolution.getHistoriqueLastSemaine();

        for (int i = 0; i < histLastSemaine.size(); i++) {
            if (histLastSemaine.get(i).getIdHistorique() == 0) {
                historiqueService.addHistorique(histLastSemaine.get(i));
            }
        }

        //Récupération du tableau pour la time line
        TimeLine[] timeLines = getTimeLine(user_resolution);

        //Envoie page
        model.addAttribute("user", user.get());
        model.addAttribute("histLastSemaine", histLastSemaine);
        model.addAttribute("user_resolution", user_resolution);
        model.addAttribute("resolutionValidationForm", new ResolutionValidationForm());
        model.addAttribute("timeLines", timeLines);

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

        //Récupération du tableau pour la time line
        TimeLine[] timeLines = getTimeLine(user_resolution);

        //Envoie page
        model.addAttribute("user", user.get());
        model.addAttribute("histLastSemaine", histLastSemaine);
        model.addAttribute("user_resolution", user_resolution);
        model.addAttribute("resolutionValidationForm", new ResolutionValidationForm());
        model.addAttribute("timeLines", timeLines);

        return "resolution";
    }



    /**
     * Permet d'obtenir le tableau pour la timeline d'une résolution d'un utilisateur
     * @return
     */
    public TimeLine[] getTimeLine(UserResolution userResolution){
        //1 - Récupération du premier jour de l'année
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), 0, 1);

        //2 - Récupération du nombre de jour dans l'année et de decalage qui correspond au nombre de jours a ajouter pour aligner la case du premier jour de l'année sur son bon jour.
        int nbJourAnnee = getNbJourAnnee(cal.get(Calendar.YEAR));
        int decalage = cal.get(Calendar.DAY_OF_WEEK) - 1;

        //3 - Création du tableau sur une annee
        TimeLine[] timeLines = new TimeLine[nbJourAnnee+decalage];

        //Ajout au début du tableau de valeur useless pour aligner
        for(int i=0; i<decalage; i++){
            timeLines[i] = new TimeLine(cal.getTime(), 2);
        }

        //Ajout des valeurs sur l'année 1 = fait, 0 = pas d'info
        for (int i = decalage; i < timeLines.length; i++) {
            //Regarde si dans la liste historiques il y a une instance d'historique avec la date cal courante et si son état de réussite est true
            Historique histCourante = new Historique(cal.getTime(), true, userResolution);

            if(userResolution.getHistoriques().contains(histCourante)){
                timeLines[i] = new TimeLine(cal.getTime(), 1);
            }
            else{
                timeLines[i] = new TimeLine(cal.getTime(), 0);
            }

            cal.add(Calendar.DATE, +1);
        }

        return timeLines;
    }

    /**
     * Permet de calculer le nombre de jour d'une année
     * @param annee
     * @return
     */
    public int getNbJourAnnee(int annee){
        Calendar cal = Calendar.getInstance();
        cal.set(1,1,annee);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }


}
