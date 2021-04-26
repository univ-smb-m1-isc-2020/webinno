package fr.webinno.form;

public class CreateAccountForm {

    private String nom;
    private String prenom;
    private String mail;
    private String pass;

    public CreateAccountForm() {
    }

    public CreateAccountForm(String nom, String prenom, String mail, String pass) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.pass = pass;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
