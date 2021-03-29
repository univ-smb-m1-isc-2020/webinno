package fr.webinno.infra.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Resolution {
    @Id
    @GeneratedValue
    private Long id;

    private String action;

    private String frequence;

    private int nbOccurence;

    public Resolution(){

    }

    public Resolution(String action, String frequence, int nbOccurence) {
        this.action = action;
        this.frequence = frequence;
        this.nbOccurence = nbOccurence;
    }

    public Long getId(){
        return id;
    }

    public String getAction(){
        return action;
    }

    public String getFrequence(){
        return frequence;
    }

    public int getNbOccurence(){
        return nbOccurence;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNbOccurence(int nbOccurence) {
        this.nbOccurence = nbOccurence;
    }
}
