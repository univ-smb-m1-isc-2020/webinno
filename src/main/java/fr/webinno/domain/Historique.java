package fr.webinno.domain;

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

    public Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public long getIdHistorique() {
        return idHistorique;
    }
}
