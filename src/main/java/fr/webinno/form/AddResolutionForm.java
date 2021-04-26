package fr.webinno.form;

import fr.webinno.domain.Frequence;

public class AddResolutionForm {
    private long idResolution;
    private long idUser;
    private Frequence frequence;
    private int nb_occurences;
    private String action;

    public AddResolutionForm(long idResolution, long idUser, Frequence frequence, int nb_occurences, String action) {
        this.idResolution = idResolution;
        this.idUser = idUser;
        this.frequence = frequence;
        this.nb_occurences = nb_occurences;
        this.action = action;
    }

    public AddResolutionForm() {
    }


    public long getIdResolution() {
        return idResolution;
    }

    public long getIdUser() {
        return idUser;
    }

    public Frequence getFrequence() {
        return frequence;
    }

    public int getNb_occurences() {
        return nb_occurences;
    }

    public String getAction() {
        return action;
    }

    public void setIdResolution(long idResolution) {
        this.idResolution = idResolution;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public void setFrequence(Frequence frequence) {
        this.frequence = frequence;
    }

    public void setNb_occurences(int nb_occurences) {
        this.nb_occurences = nb_occurences;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
