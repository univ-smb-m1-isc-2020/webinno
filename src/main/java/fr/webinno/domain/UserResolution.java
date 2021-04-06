package fr.webinno.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserResolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserResolution;

    @Enumerated(EnumType.STRING)
    private Frequence frequence;

    private int nbOccurence;

    @ManyToOne @JoinColumn(name="idUser", nullable = false)
    private User user;

    @ManyToOne @JoinColumn(name="idResolution", nullable = false)
    private Resolution resolution;

    @OneToMany( targetEntity = Historique.class, mappedBy = "userResolution" )
    private List<Historique> historiques = new ArrayList<>();

    public UserResolution(Frequence frequence, int nbOccurence, User user, Resolution resolution) {
        this.frequence = frequence;
        this.nbOccurence = nbOccurence;
        this.user = user;
        this.resolution = resolution;
    }

    public UserResolution() {

    }

    public long getIdUserResolution() {
        return idUserResolution;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public List<Historique> getHistoriques() {
        return historiques;
    }

    public String toString(){
        return "User : " + user.getName()+ ", Action : " + resolution.getAction() + ", Frequence : " + frequence + " Nb_occ : " + nbOccurence;
    }
}
