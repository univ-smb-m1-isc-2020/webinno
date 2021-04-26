package fr.webinno.form;

import fr.webinno.domain.Frequence;

public class AddUserResolutionForm {
    private long idResolution;
    private long idUser;
    private Frequence frequence;
    private int nb_occurences;

    public AddUserResolutionForm(long idResolution, long idUser, Frequence frequence, int nb_occurences) {
        this.idResolution = idResolution;
        this.idUser = idUser;
        this.frequence = frequence;
        this.nb_occurences = nb_occurences;
    }

    public AddUserResolutionForm() {
    }

    public long getIdResolution() {
        return idResolution;
    }

    public void setIdResolution(long idResolution) {
        this.idResolution = idResolution;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public Frequence getFrequence() {
        return frequence;
    }

    public void setFrequence(Frequence frequence) {
        this.frequence = frequence;
    }

    public int getNb_occurences() {
        return nb_occurences;
    }

    public void setNb_occurences(int nb_occurences) {
        this.nb_occurences = nb_occurences;
    }
}
