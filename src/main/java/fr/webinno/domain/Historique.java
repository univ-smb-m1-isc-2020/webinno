package fr.webinno.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Historique {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idHistorique;

    private Date date;

    private boolean done;

    @ManyToOne @JoinColumn(name="idUserResolution", nullable = false)
    private UserResolution userResolution;

}
