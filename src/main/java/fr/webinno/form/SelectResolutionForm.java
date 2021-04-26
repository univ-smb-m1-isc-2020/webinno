package fr.webinno.form;

public class SelectResolutionForm {
    private long idUser;
    private long idResolution;

    public SelectResolutionForm() {
    }

    public SelectResolutionForm(long idUser, long idResolution) {
        this.idUser = idUser;
        this.idResolution = idResolution;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdResolution() {
        return idResolution;
    }

    public void setIdResolution(long idResolution) {
        this.idResolution = idResolution;
    }
}
