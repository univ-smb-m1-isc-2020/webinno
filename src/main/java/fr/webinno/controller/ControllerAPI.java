package fr.webinno.controller;

import fr.webinno.domain.*;
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
    public List<ResolutionApi> getResolutions(){
        List<ResolutionApi> resolutions = new ArrayList<ResolutionApi>();
        List<Resolution> resolutionList = resolutionService.getAllResolutions();
        for (Resolution resolution : resolutionList) {
            resolutions.add(new ResolutionApi(resolution.getIdResolution(),resolution.getAction()));
        }
        return resolutions;
    }

    /**
     * Permet de récuperer les résolutions d'un utilisateur via son id
     * @param idUser id de l'utilisateur
     * @return la liste de résolution de notre utilisateur
     */
    @GetMapping("/getResolutionsOfUser")
    public List<ResolutionApi> getResolutionsOfUser(@RequestParam(value="idUser")long idUser){
        var user = userService.getUserById(idUser);
        List<UserResolution> urList = user.get().getUserResolutions();
        List<ResolutionApi> resolutions = new ArrayList<ResolutionApi>();
        for(int i=0;i<urList.size();i++){
                resolutions.add(new ResolutionApi(urList.get(i).getResolution().getIdResolution(),urList.get(i).getResolution().getAction(),urList.get(i).getFrequence(),urList.get(i).getNbOccurence()));
        }
        return resolutions;
    }

    @GetMapping("/connectUser")
    public String connectUser(@RequestParam(value="login") String login,@RequestParam(value="pass") String pass){
        var user= userService.getUserByName(login);
        if(user != null){
            if(user.login(pass)){
                return "{resultat:true,idUser:"+user.getIdUser()+"}";
            }
        }
        return "{resultat:false}";
    }

    @GetMapping("/getNotResolutions")
    public List<ResolutionApi> getNotResolutions(@RequestParam(value="idUser") long idUser){
        var user = userService.getUserById(idUser);
        List<Resolution> resolutions = resolutionService.getAllResolutions();
        List<ResolutionApi> res = new ArrayList<ResolutionApi>();
        for(int i=0;i<user.get().getUserResolutions().size();i++){
            resolutions.remove(user.get().getUserResolutions().get(i).getResolution());
        }
        for(Resolution resolution: resolutions){
            res.add(new ResolutionApi(resolution.getIdResolution(),resolution.getAction()));
        }
        return res;
    }

    @GetMapping("/takeResolution")
    public String takeResolution(@RequestParam(value="idUser") long idUser, @RequestParam(value="idResolution") long idResolution, @RequestParam(value="frequence")Frequence frequence,@RequestParam(value="nbOccurences") int nbOccurences){

        var user = userService.getUserById(idUser);
        var resolution = resolutionService.getById(idResolution);
        try {
            userResolutionService.addUserResolution(new UserResolution(frequence, nbOccurences, user.get(), resolution.get()));
            return "{resultat:true}";
        }catch (Exception e){
            e.printStackTrace();
            return "{resultat:false}";
        }

    }

}
