package fr.webinno.controller;

import fr.webinno.domain.Resolution;
import fr.webinno.domain.User;
import fr.webinno.domain.UserResolution;
import fr.webinno.service.HistoriqueService;
import fr.webinno.service.ResolutionService;
import fr.webinno.service.UserResolutionService;
import fr.webinno.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControllerAPI {
    private final ResolutionService resolutionService;
    private final UserService userService;
    private final UserResolutionService userResolutionService;
    private final HistoriqueService historiqueService;

    @Autowired
    public ControllerAPI(ResolutionService resolutionService, UserService userService, UserResolutionService userResolutionService, HistoriqueService historiqueService) {
        this.resolutionService = resolutionService;
        this.userService = userService;
        this.userResolutionService = userResolutionService;
        this.historiqueService = historiqueService;
    }

    /**
     * Permet de recuperer l'ensemble des résolutions de la bdd
     * @return une liste de resolutions
     */
    @GetMapping("/getResolutions")
    public List<Resolution> getResolutions(){
        return resolutionService.getAllResolutions();
    }

    /**
     * Permet de récuperer les résolutions d'un utilisateur via son id
     * @param idUser id de l'utilisateur
     * @return la liste de résolution de notre utilisateur
     */
    @GetMapping("/getResolutionsOfUser")
    public List<Resolution> getResolutionsOfUser(@RequestParam(value="idUser")long idUser){
        var user = userService.getUserById(idUser);
        List<UserResolution> urList = user.get().getUserResolutions();
        List<Resolution> resolutions = new ArrayList<Resolution>();
        for(int i=0;i<urList.size();i++){
                resolutions.add(urList.get(i).getResolution());
        }
        return resolutions;
    }

    @GetMapping("/connectUser")
    public Boolean connectUser(@RequestParam(value="login") String login,@RequestParam(value="pass") String pass){
        var user= userService.getUserByName(login);
        if(user != null){
            if(user.login(pass)){
                return true;
            }
        }
        return false;
    }

    @GetMapping("/getNotResolutions")
    public List<Resolution> getNotResolutions(@RequestParam(value="idUser") long idUser){
        var user = userService.getUserById(idUser);
        List<Resolution> resolutions = resolutionService.getAllResolutions();
        for(int i=0;i<user.get().getUserResolutions().size();i++){
            resolutions.remove(user.get().getUserResolutions().get(i).getResolution());
        }
        return resolutions;
    }

    @GetMapping("/takeResolution")
    public Boolean takeResolution(@RequestParam(value="idUser") long idUser,@RequestParam(value="idResolution") long idResolution){

        var user = userService.getUserById(idUser);
        var resolution = resolutionService.getById(idResolution);
        try {
            userResolutionService.addUserResolution(new UserResolution(resolution.get().getFrequence(), resolution.get().getNbOccurence(), user.get(), resolution.get()));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
