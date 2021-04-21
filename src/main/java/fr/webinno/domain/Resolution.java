package fr.webinno.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resolution {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idResolution;

    private String action;

    @Enumerated(EnumType.STRING)
    private Frequence frequence;

    private int nbOccurence;

    @OneToMany( targetEntity = UserResolution.class, mappedBy = "resolution" )
    private List<UserResolution> userResolutions = new ArrayList<>();

    public Resolution(){

    }

    public Resolution(String action, Frequence frequence, int nbOccurence) {
        this.frequence = frequence;
        this.nbOccurence = nbOccurence;
        this.action = action;
    }

    public Long getIdResolution(){
        return idResolution;
    }

    public String getAction(){
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<UserResolution> getUserResolutions() {
        return userResolutions;
    }

    public String toString(){
        String str = this.action + " [ ";

        return str;
    }

    public void setIdResolution(Long idResolution) {
        this.idResolution = idResolution;
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
