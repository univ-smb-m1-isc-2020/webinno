package fr.webinno.form;

import fr.webinno.domain.UserResolution;

import java.util.Date;

public class ResolutionValidationForm {

    private long idUser;
    private long idHistorique;

    public ResolutionValidationForm() {
    }

    public ResolutionValidationForm(long idUser, long idHistorique) {
        this.idUser = idUser;
        this.idHistorique = idHistorique;
    }


    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(long idHistorique) {
        this.idHistorique = idHistorique;
    }

}
