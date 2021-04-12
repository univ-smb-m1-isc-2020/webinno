package fr.webinno.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    //--------------------------------------------------------
    //                    Attributes
    //--------------------------------------------------------
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idUser;

    private String name;

    private String surname;

    private String mail;

    private String password;

    @OneToMany( targetEntity = UserResolution.class, mappedBy = "user" )
    private List<UserResolution> userResolutions = new ArrayList<>();


    //--------------------------------------------------------
    //                    Constructors
    //--------------------------------------------------------
    public User(){

    }

    public User(String name, String surname, String mail, String password) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
    }

    //--------------------------------------------------------
    //                   Getteur & Setteur
    //--------------------------------------------------------
    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserResolution> getUserResolutions() {
        return userResolutions;
    }

    //--------------------------------------------------------
    //                   Methods
    //--------------------------------------------------------
    public String toString(){
        String str = "------------[USER]-------------\n" +
                " id : " + idUser +
                " name : " + name +
                " surname : " + surname +
                " email : " + mail +
                " password : " + password +
                " resolutions : " + "todo";
        return str;
    }

    public boolean login(String passwordTest){
        if(this.password.compareTo(passwordTest) == 0){
            return true;
        }
        return false;
    }
}
