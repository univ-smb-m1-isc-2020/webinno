package fr.webinno.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    /**
     * Vérifie si
     * @return
     */
    public boolean dejaValideJour(){
        //Récupération date du jour
        Date jour = new Date();

        for(int it=0; it<historiques.size(); it++){
            if(historiques.get(it).sameDate(jour)){
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de récupérer l'historique sur une derniere semaine
     * @return
     */
    public ArrayList<Historique> getHistoriqueLastSemaine(){
        Date dateJour = new Date();

        Calendar calDateJour = Calendar.getInstance();
        calDateJour.setTime(dateJour);

        ArrayList<Historique> histLastSemaine = new ArrayList<>();

        //du jour j au j-7
        for(int itJour=0; itJour<7; itJour++){
            //Regarde si donné dans historique
            Historique histCourant = null;
            for(int it=0; it<historiques.size(); it++){
                if(historiques.get(it).sameDate(dateJour)){
                    histCourant = historiques.get(it);
                }
            }

            //Si trouvé on le met dans la liste
            if(histCourant != null){
                histLastSemaine.add(histCourant);
            }
            //Sinon on met comme pas fait
            else{
                histLastSemaine.add(new Historique(dateJour, false, this));
            }

            //Décalage du jour
            calDateJour.add(Calendar.DATE, -1);
            dateJour = calDateJour.getTime();
        }
        return histLastSemaine;
    }

    public void updateHistorique(Date date, boolean validation){
        for(int it=0; it<historiques.size(); it++){
            if(historiques.get(it).sameDate(date)){
                historiques.get(it).setDone(validation);
            }
        }
    }
}
