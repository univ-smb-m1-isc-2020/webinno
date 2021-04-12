package fr.webinno.controller;

import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;
import fr.webinno.form.AddUserResolutionForm;
import fr.webinno.form.LoginForm;
import fr.webinno.form.SelectResolutionForm;
import fr.webinno.service.HistoriqueService;
import fr.webinno.service.ResolutionService;
import fr.webinno.service.UserResolutionService;
import fr.webinno.service.UserService;
import fr.webinno.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        System.out.println("======================TRY LOGIN===================================");

        // 1 - Récupération donnée du formulaire
        var user = userService.getUserByName(loginForm.getUserName());
        var password = loginForm.getPassword();

        System.out.println("NAME :" +loginForm.getUserName());
        System.out.println("PWD :" +loginForm.getPassword());
        System.out.println("USER = " + user);


        if(user == null){
            return index(model);
        }

        // 2 - Vérification pour la connexion
        if(user.login(password)){
            System.out.println("=====================LOGIN OK==============================");
            // 3 - Redirection
            var resolutions = resolutionService.getAllResolutions();
            model.addAttribute("resolutions", resolutions);
            return user(model, user);
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

    public String user(Model model, User user){
        model.addAttribute("user", user);

        var user_resolutions = userResolutionService.getAllUserResolutionByUser(user);
        model.addAttribute("user_resolutions", user_resolutions);

        return "user";
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
