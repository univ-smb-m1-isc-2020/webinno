package fr.webinno.controller;


import fr.webinno.domain.Historique;
import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;

import fr.webinno.domain.*;

import fr.webinno.form.*;
import fr.webinno.service.HistoriqueService;
import fr.webinno.service.ResolutionService;
import fr.webinno.service.UserResolutionService;
import fr.webinno.service.UserService;

import org.joda.time.DateTime;
import org.joda.time.Days;
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
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

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
    public String index(Model model, HttpSession session){
        Map userResolutions = new HashMap();
        Map effectif = new HashMap();


        ////////////////////////////////////////////////////////////
        //                         IMPORTANT                      //
        //à mettre dans le login pour garder l'utilisateur courant//
        ////////////////////////////////////////////////////////////
        //session.setAttribute("idusergen",2);


        Map pourcentage = new HashMap();

        List<Resolution> resolutions = resolutionService.getAllResolutions();
        List<User> users = userService.getAllUsers();
        for(int i=0;i<resolutions.size();i++){

            List<UserResolution> res = userResolutionService.getAllUserResolutionByResolution(resolutions.get(i));
            userResolutions.put(resolutions.get(i).getIdResolution(),res);
            effectif.put(resolutions.get(i).getIdResolution(),res.size());
        }


        //Pourcentage global de chaque résolution
        for(int k=0;k<resolutions.size();k++) {
            //1- on recupère la résolution
            Resolution r = resolutions.get(k);
            //2- on recupère les userResolution de la résolution cela permet de connaitre les utilisateurs qui on pris cette résolution
            List<UserResolution> ur = r.getUserResolutions();

            List<String> dates = new ArrayList<String>();

            //5- on recupère les historiques de chaque personne
            int nbReussit = 0;
            int nbTotal = 0;
            for (int i = 0; i < ur.size(); i++) {
                //3- on recupère la fréquence (jour/mois/année) pour connaitre la période à vérifier
                Frequence freq = ur.get(i).getFrequence();
                //4- on recupère le nombre d'occurences au dela duquel on a réussit/validé la résolution
                int occurence = ur.get(i).getNbOccurence();


                //5- on recupère la date du jour et on fait la liste des jours à verifier en fonction de la fréquence
                DateTime date = new DateTime();



                if (freq == Frequence.JOUR) {

                    dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());

                } else if (freq == Frequence.SEMAINE) {
                    dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());

                    int days = Days.daysBetween(date, date.minusWeeks(1)).getDays();
                    for (int j = days + 1; j < 0; j++) {
                        date = date.minusDays(1);
                        dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                    }

                } else if (freq == Frequence.MOIS) {
                    dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                    int days = Days.daysBetween(date, date.minusMonths(1)).getDays();
                    for (int j = days + 1; j < 0; j++) {
                        date = date.minusDays(1);
                        dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                    }

                } else if (freq == Frequence.ANNEE) {
                    dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                    int days = Days.daysBetween(date, date.minusYears(1)).getDays();
                    for (int j = days + 1; j < 0; j++) {
                        date = date.minusDays(1);
                        dates.add(date.getDayOfMonth() + "-" + date.getMonthOfYear() + "-" + date.getYear());
                    }
                }
                List<Historique> historique = ur.get(i).getHistoriques();
                //6- on regarde dans l'historique si la résolution est valide dans la dernière periode pour cette personne
                int cptOccur = 0;
                for (int j = 0; j < historique.size(); j++) {
                    DateTime db = new DateTime(historique.get(j).getDate());
                    if (dates.contains(db.getDayOfMonth() + "-" + db.getMonthOfYear() + "-" + db.getYear())) {
                        if (historique.get(j).isDone()) {
                            cptOccur++;
                        }

                    }
                }
                nbTotal++;
                if (cptOccur >= occurence) {
                    nbReussit++;
                }
            }

            String p = "0%";;
            if(nbTotal > 0) {
                p = "" + ((int) ((float) nbReussit / nbTotal * 100.0)) + "%";
            }
            pourcentage.put(r.getIdResolution(),p);
        }


        model.addAttribute("userResolutions",userResolutions);
        model.addAttribute("effectif",effectif);
        model.addAttribute("resolutions",resolutions);
        model.addAttribute("pourcentage",pourcentage);
        return "index";
    }

    @GetMapping("/addResolution")
    public String addResolution(Model model,HttpSession session){
        List<Resolution> resolutions = resolutionService.getAllResolutions();
        Map populaire = new HashMap();
        List<Resolution> resolutionsPopulaire = new ArrayList<Resolution>();

        //On recupère notre utilisateur
        User user = (User) session.getAttribute("user");

        List<UserResolution> mesRes = userResolutionService.getAllUserResolutionByUser(user);
        List<Resolution> resolutionsHasard = new ArrayList<Resolution>();
        List<Long> idres = new ArrayList<Long>();

        //on ajoute les résolutions que l'utilisateur possède déjà pour eviter de les tirer au hasard ou dans les plus populaires
        for(int i=0;i<mesRes.size();i++){
            idres.add(mesRes.get(i).getResolution().getIdResolution());
        }

        for (int i = 0;i<resolutions.size();i++){
            List<UserResolution> res = resolutions.get(i).getUserResolutions();
            //recup la taille de chaque résolution
            populaire.put(i,res.size());
        }

        //triage de la liste des resolutions par popularité
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(populaire.entrySet());
        list.sort(Map.Entry.comparingByValue());
        int x =0;


        while(resolutionsPopulaire.size()<3 && x<list.size()){
            if(idres.contains(resolutions.get(list.get(x).getKey()).getIdResolution())){

            }else{
                resolutionsPopulaire.add(resolutions.get(list.get(x).getKey()));
                //on ajoute dans les résolutions a ne pas tirer les résolution populaires retenue
                idres.add(resolutions.get(list.get(x).getKey()).getIdResolution());
            }
            x++;
        }




        int i=0;

        //on prend 10 résolution si possibles sinon on prend le maximum (ensemble des resolutions auxquelles on enlèves celle que l'utilisateur possède et les populaire qui ont été ajouté juste avant
        int max = resolutions.size()-idres.size();
        while(resolutionsHasard.size()<10 && resolutionsHasard.size()<max){
            //on tire une résolution random
            double chiffre = Math.random() * ( resolutions.size() - 0 );
            //on vérifie que cette résolution à le droit d'être prise (= ni populaire ni déjà prise par l'utilisateur)
            if(idres.contains(resolutions.get((int) chiffre).getIdResolution())){

            }else{
                resolutionsHasard.add(resolutions.get((int) chiffre));
                //on ajoute chaque resolutions dans celle qui n'ont pas le droit d'être prises (pour eviter des duplications)
                idres.add(resolutions.get((int) chiffre).getIdResolution());
            }
            i++;
        }


        model.addAttribute("resolutionPopulaires",resolutionsPopulaire);
        model.addAttribute("resolutionHasard",resolutionsHasard);
        model.addAttribute("addResolutionForm", new AddResolutionForm());
        model.addAttribute("addUserResolutionForm", new AddUserResolutionForm());
        return "addResolution";
    }

    @PostMapping("/addNewResolution")
    public String addNewResolution(@ModelAttribute AddResolutionForm addResolutionForm, Model model,HttpSession session){
        //Création de l'utilisateur"
        User user = userService.getUserById(addResolutionForm.getIdUser()).get();

        //Création de la résolution
        Resolution r = new Resolution(addResolutionForm.getAction());

        //Ajout de la resolution dans la BDD
        resolutionService.addResolution(r);

        //Ajout de la user_resolution dans la BDD (on estime que la personne qui crée la résolution la prend imédiatement)
        userResolutionService.addUserResolution(new UserResolution(addResolutionForm.getFrequence(),addResolutionForm.getNb_occurences(),user,r));

        return index(model,session);
    }

    @GetMapping("/myResolutions")
    public String myResolutions(Model model,HttpSession session){

        //On recupère notre utilisateur et ses résolutions
        User user = (User) session.getAttribute("user");
        List<UserResolution> mesRes = userResolutionService.getAllUserResolutionByUser(user);

        Map frequence = new HashMap();
        List<Resolution> resolutions = new ArrayList<Resolution>();

        for(int i=0;i<mesRes.size();i++){
            resolutions.add(mesRes.get(i).getResolution());

            String freq = mesRes.get(i).getNbOccurence() + " fois / " + mesRes.get(i).getFrequence();
            frequence.put(mesRes.get(i).getResolution().getIdResolution(),freq.toLowerCase(Locale.ROOT));
        }

        model.addAttribute("resolutions",resolutions);
        model.addAttribute("frequence",frequence);
        model.addAttribute("userResolutionForm",new SelectResolutionForm());
        return "myResolutions";
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


    @PostMapping("/addUserResolution")
    public String addUserResolution(@ModelAttribute AddUserResolutionForm addUserResolutionForm, Model model,HttpSession session){

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

        return index(model,session);
    }


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute(new LoginForm());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model,HttpSession session){
        session.removeAttribute("user");

        return index(model,session);
    }


    @PostMapping("/tryLogin")
    public String tryLogin(@ModelAttribute LoginForm loginForm, Model model, HttpSession session){
        // 1 - Récupération donnée du formulaire
        var user = userService.getUserByName(loginForm.getUserName());
        var password = loginForm.getPassword();

        if(user == null){
            return index(model,session);
        }

        // 2 - Vérification pour la connexion
        if(user.login(password)){
            // 3 - Redirection
            var resolutions = resolutionService.getAllResolutions();
            model.addAttribute("resolutions", resolutions);
            session.setAttribute("user",user);
            return myResolutions(model, session);
        }
        else{
            return index(model,session);
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
