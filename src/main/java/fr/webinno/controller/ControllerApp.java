package fr.webinno.controller;

import fr.webinno.domain.*;
import fr.webinno.form.AddUserResolutionForm;
import fr.webinno.form.LoginForm;
import fr.webinno.form.SelectResolutionForm;
import fr.webinno.service.HistoriqueService;
import fr.webinno.service.ResolutionService;
import fr.webinno.service.UserResolutionService;
import fr.webinno.service.UserService;
import fr.webinno.form.UserForm;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

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
        Map userResolutions = new HashMap();
        Map effectif = new HashMap();
        Map frequence = new HashMap();

        //Pour le pourcentage
        //recup chaque user
            //recup l'historique de chaque user
                //regarder si l'historique au moins eqivalent à la fréquence
                //si oui
                    //cpt++ pour cette résolution
                //sinon
                    //rien
            //ajouter le cpt/nbuser de la résolution dans le map(idResolution, cpt/effictif.get(i));

        Map pourcentage = new HashMap();

        List<Resolution> resolutions = resolutionService.getAllResolutions();
        List<User> users = userService.getAllUsers();
        for(int i=0;i<resolutions.size();i++){

            List<UserResolution> res = userResolutionService.getAllUserResolutionByResolution(resolutions.get(i));
            userResolutions.put(resolutions.get(i).getIdResolution(),res);
            effectif.put(resolutions.get(i).getIdResolution(),res.size());
            if(res.size() > 0){
                String freq = res.get(0).getNbOccurence() + " fois / " + res.get(0).getFrequence();
                frequence.put(resolutions.get(i).getIdResolution(),freq.toLowerCase(Locale.ROOT));
            }else {
                frequence.put(resolutions.get(i).getIdResolution(), 0);
            }

            //System.err.println(res.get(0).getHistoriques().size());

        }
        ////////////////////
        //TEST POURCENTAGE// (sur une résolution)
        ////////////////////

        for(int k=0;k<resolutions.size();k++) {
            //1- on recup la résolution
            Resolution r = resolutions.get(k);

            //2- on recup les user-resolution de la resolution
            List<UserResolution> ur = userResolutionService.getAllUserResolutionByResolution(r);
            System.err.println(r);
            //3- on recup la fréquence (jour/mois/année) pour connaitre la période sur laquelle on regarde la réussite
            Frequence freq = ur.get(0).getFrequence();
            //4- on recup le nombre d'occurence au dela duquel on a réussit
            int occurence = ur.get(0).getNbOccurence();

            //5- on recupère la date du jour et on fait la liste des jours à regarder en fonction de la frequence
            DateTime date = new DateTime();

            List<String> dates = new ArrayList<String>();
            System.err.println(date);
            System.err.println(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());

            if (freq == Frequence.JOUR) {

                dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());

            } else if (freq == Frequence.SEMAINE) {
                dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());

                int days = Days.daysBetween(date, date.minusWeeks(1)).getDays();
                for (int i = days + 1; i < 0; i++) {
                    date = date.minusDays(1);
                    dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                }

            } else if (freq == Frequence.MOIS) {
                dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                int days = Days.daysBetween(date, date.minusMonths(1)).getDays();
                for (int i = days + 1; i < 0; i++) {
                    date = date.minusDays(1);
                    dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                }

            } else if (freq == Frequence.ANNEE) {
                dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                int days = Days.daysBetween(date, date.minusYears(1)).getDays();
                for (int i = days + 1; i < 0; i++) {
                    date = date.minusDays(1);
                    dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                }
            }
            System.err.println(dates.toString());

            //5- on recup les historiques de chaque personnes
            int nbReussit = 0;
            int nbTotal = 0;
            for (int i = 0; i < ur.size(); i++) {
                List<Historique> historique = ur.get(i).getHistoriques();
                //6- on regarde dans l'historique si valide dans la dernière periode
                int cptOccur = 0;
                for (int j = 0; j < historique.size(); j++) {
                    DateTime db = new DateTime(historique.get(j).getDate());
                    System.out.println(db.getDayOfMonth() + "-" + db.getMonthOfYear() + "-" + db.getYear());
                    if (dates.contains(db.getDayOfMonth() + "-" + db.getMonthOfYear() + "-" + db.getYear())) {
                        System.err.println(db);
                        if (historique.get(j).isDone()) {
                            cptOccur++;
                            System.out.println("DONE");
                        }

                    }
                }
                nbTotal++;
                if (cptOccur >= occurence) {
                    nbReussit++;
                }
            }

            System.err.println(nbReussit);
            System.err.println(nbTotal);
            System.out.println((float) nbReussit / nbTotal * 100.0);
            String p = ""+((float) nbReussit / nbTotal * 100.0)+"% (Du "+dates.get(dates.size()-1)+" au "+dates.get(0)+")";
            pourcentage.put(r.getIdResolution(),p);
        }
        //System.err.println(ur.get(0).getHistoriques().toString());
        //System.err.println(ur.get(0).getUser().getName());
        //System.err.println(ur.get(0).getHistoriques().get(0).getDate());


        model.addAttribute("frequence",frequence);
        model.addAttribute("userResolutions",userResolutions);
        model.addAttribute("effectif",effectif);
        model.addAttribute("resolutions",resolutions);
        model.addAttribute("pourcentage",pourcentage);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute(new LoginForm());
        return "login";
    }


    @PostMapping("/tryLogin")
    public String tryLogin(@ModelAttribute LoginForm loginForm, Model model){
        //TODO récupérer la checkbox remenberMe

        System.out.println("USERNAME : " +loginForm.getUserName());
        System.out.println("PASSWORD : " +loginForm.getPassword());

        // 1 - Récupération donnée du formulaire
        var user = userService.getUserByName(loginForm.getUserName());
        var password = loginForm.getPassword();

        if(user == null){
            return index(model);
        }
        System.out.println("TEST USER" +user);

        // 2 - Vérification pour la connexion
        if(user.login(password)){
            System.out.println("=====================LOGIN OK==============================");
            // 3 - Redirection
            var resolutions = resolutionService.getAllResolutions();
            model.addAttribute("resolutions", resolutions);
            return users(model);
        }
        else{
            System.out.println("=============================LOGIN PAS OK=====================================");
            return index(model);
        }
    }

    /*
        Pour afficher la liste des resolutions existante
     */
    @GetMapping("/showResolutions")
    public String getResolutions(Model model){
        var resolutions = resolutionService.getAllResolutions();
        model.addAttribute("resolutions", resolutions);

        return "resolution";
    }

    /*
    Pour afficher la liste des users et pouvoir en sélectionner un
     */
    @GetMapping(value="/users")
    public String users(Model model){
        var users = userService.getAllUsers();
        model.addAttribute("users", users);

        model.addAttribute("userForm", new UserForm());
        return "users";
    }

    /*
        Pour afficher un user et pouvoir lui ajouter une résolution
     */
    @PostMapping("/selectUser")
    public String user(@ModelAttribute UserForm userForm, Model model){
        //1 - Récupération de l'user
        var user = userService.getUserById(userForm.getIdUser());

        //2 - Vérification si trouvé
        if(!user.isPresent()){
            System.out.println("Utilisateur non trouvé");
            return "login";
        }
        else{
            model.addAttribute("user", user.get());

            System.out.println("User trouvé :" + user.get());

            // Récupération de ces Résolutions de l'user
            var user_resolutions = userResolutionService.getAllUserResolutionByUser(user.get());
            model.addAttribute("user_resolutions", user_resolutions);



            //3 - Récupérations des resolutions TODO prendre uniquement celle qu'il ne possede pas déja
            var resolutions = resolutionService.getAllResolutions();
            model.addAttribute("resolutions", resolutions);

            // Envoie du form
            model.addAttribute("selectResolutionForm", new SelectResolutionForm());

            return "user";
        }
    }


    @PostMapping("/addUserResolutionForm")
    public String addUserResolutionForm(@ModelAttribute SelectResolutionForm selectResolutionForm, Model model){
        //1 - Récupération de la résolution
        var resolution = resolutionService.getById(selectResolutionForm.getIdResolution());
        if(!resolution.isPresent()){
            System.err.println("[ControllerApp] /addResolution Resolution non trouvé");
            //TODO traitement err
            return "login";
        }

        //2 - Récupération de l'user
        var user = userService.getUserById(selectResolutionForm.getIdUser());
        if(!user.isPresent()){
            //TODO error
            System.err.println("[ControllerApp] /addResolution, error user not find");
            return "login";
        }

        //3 - Envoi data
        model.addAttribute("resolution", resolution.get());
        model.addAttribute("user", user.get());
        model.addAttribute("addUserResolutionForm", new AddUserResolutionForm());

        return "addUserResolutionForm";
    }

    @PostMapping("/addUserResolution")
    public String addUserResolution(@ModelAttribute AddUserResolutionForm addUserResolutionForm, Model model){

        // 1 - Récupération de l'user
        var user = userService.getUserById(addUserResolutionForm.getIdUser());
        if(!user.isPresent()){
            System.err.println("[ControllerApp] /addUserResolution, user not found !");
            return "login";
        }

        // 2 - Récupération de la résolution
        var resolution = resolutionService.getById(addUserResolutionForm.getIdResolution());
        if(!resolution.isPresent()){
            System.err.println("[ControllerApp] /addUserResolution, resolution not found !");
            return "login";
        }

        // 3 - Création de l'UserResolution
        UserResolution ur = new UserResolution(addUserResolutionForm.getFrequence(), addUserResolutionForm.getNb_occurences(), user.get(), resolution.get() );
        System.out.println("Creation de la nouvelle UserResolution : " + ur);

        userResolutionService.addUserResolution(ur);
        // 4 - Récupération informations pour la page user
        model.addAttribute("user", user.get());

        var user_resolutions = userResolutionService.getAllUserResolutionByUser(user.get());
        model.addAttribute("user_resolutions", user_resolutions);

        var resolutions = resolutionService.getAllResolutions();
        model.addAttribute("resolutions", resolutions);

        model.addAttribute("selectResolutionForm", new SelectResolutionForm());
        return "user";
    }
}
