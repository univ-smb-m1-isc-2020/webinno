package fr.webinno.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ResolutionApi {

    private Long idResolution;

    private String action;

    private Frequence frequence;

    private int nbOccurence;

    public ResolutionApi(Long idResolution, String action, Frequence frequence, int nbOccurence) {
        this.idResolution = idResolution;
        this.action = action;
        this.frequence = frequence;
        this.nbOccurence = nbOccurence;
    }

    public Long getIdResolution() {
        return idResolution;
    }

    public void setIdResolution(Long idResolution) {
        this.idResolution = idResolution;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Frequence getFrequence() {
        return frequence;
    }

    public void setFrequence(Frequence frequence) {
        this.frequence = frequence;
    }

    public int getNbOccurence() {
        return nbOccurence;
    }

    public void setNbOccurence(int nbOccurence) {
        this.nbOccurence = nbOccurence;
    }


}
