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

    @OneToMany( targetEntity = UserResolution.class, mappedBy = "resolution" )
    private List<UserResolution> userResolutions = new ArrayList<>();

    public Resolution(){

    }

    public Resolution(String action) {
        this.action = action;
    }

    public Resolution(Long idResolution,String action) {
        this.action = action;
        this.idResolution = idResolution;
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
}
