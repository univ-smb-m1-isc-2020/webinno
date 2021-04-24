package fr.webinno.domain;

import fr.webinno.repository.HistoriqueRepository;
import fr.webinno.service.HistoriqueImpl;
import fr.webinno.service.HistoriqueService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Historique {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idHistorique;

    private Date date;

    private boolean done;

    @ManyToOne @JoinColumn(name="idUserResolution", nullable = false)
    private UserResolution userResolution;

    public Historique(){
    }

    public Historique(Date date, boolean done, UserResolution userResolution){
        this.date = date;
        this.done = done;
        this.userResolution = userResolution;
    }

    public String toString(){
        return "-> id: " + idHistorique + ", date : " + date + ", fait : " + done;
    }

    /**
     * Compare si la date de l'historique est la même que celle passer en parametre (jour mois annee)
     * @param d
     * @return
     */
    public boolean sameDate(Date d){
        Calendar calDate = Calendar.getInstance();
        Calendar calDateCompare = Calendar.getInstance();

        calDate.setTime(date);
        calDateCompare.setTime(d);

        if(calDate.get(Calendar.DATE) == calDateCompare.get(Calendar.DATE) && calDate.get(Calendar.MONTH) == calDateCompare.get(Calendar.MONTH) && calDate.get(Calendar.YEAR) == calDateCompare.get(Calendar.YEAR)) {
            return true;
        }

        return false;
    }

    public boolean equals(Object x){
        if( x instanceof Historique){
            Historique hist = (Historique) x;
            if(hist.isDone() == this.done && hist.userResolution.equals(this.userResolution) && this.sameDate(hist.getDate())){
                return true;
            }
        }
        return false;
    }

    public long getIdHistorique(){
        return idHistorique;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }


    public void setDone(boolean done) {
        this.done = done;
    }

    public UserResolution getUserResolution(){ return this.userResolution; }

}
