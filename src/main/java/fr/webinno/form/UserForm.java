package fr.webinno.form;

public class UserForm {
    private long idUser;

    public UserForm() {
    }

    public UserForm(long idUser) {
        this.idUser = idUser;
    }

    public long getIdUser(){
        return idUser;
    }

    public void setIdUser(long idUser){
        this.idUser = idUser;
    }
}
