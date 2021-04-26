package fr.webinno.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user = new User("Jean", "Jacques", "jean.jacques@gmail.com", "jeanjacques");
    Resolution resolution = new Resolution("Test des classes");

    @Test
    void getIdUser() {
        assertThat(user.getIdUser()).isEqualTo(null);
    }

    @Test
    void getName() {
        assertThat(user.getName()).isEqualTo("Jean");
    }

    @Test
    void setName() {
        user.setName("Jean");

        assertThat(user.getName()).isEqualTo("Jean");

        user.setName("Luc");

        assertThat(user.getName()).isEqualTo("Luc");
    }

    @Test
    void getSurname() {
        assertThat(user.getSurname()).isEqualTo("Jacques");
    }

    @Test
    void setSurname() {
        user.setSurname("Jacques");

        assertThat(user.getSurname()).isEqualTo("Jacques");

        user.setSurname("Blanc");

        assertThat(user.getSurname()).isEqualTo("Blanc");
    }

    @Test
    void getMail() {
        assertThat(user.getMail()).isEqualTo("jean.jacques@gmail.com");
    }

    @Test
    void setMail() {
        user.setMail("jean.jacques@gmail.com");

        assertThat(user.getMail()).isEqualTo("jean.jacques@gmail.com");

        user.setMail("luc.blanc@gmail.com");

        assertThat(user.getMail()).isEqualTo("luc.blanc@gmail.com");
    }

    @Test
    void getPassword() {
        assertThat(user.getPassword()).isEqualTo("jeanjacques");
    }

    @Test
    void setPassword() {
        user.setPassword("jeanjacques");

        assertThat(user.getPassword()).isEqualTo("jeanjacques");

        user.setPassword("lucblanc");

        assertThat(user.getPassword()).isEqualTo("lucblanc");
    }

    @Test
    void getUserResolutions() {
        assertThat(user.getUserResolutions()).isEqualTo(new ArrayList<UserResolution>());

        UserResolution userResolution = new UserResolution(Frequence.SEMAINE, 2, user, resolution);
        List<UserResolution> userResolutionList = new ArrayList<UserResolution>();
        userResolutionList.add(userResolution);

        user.getUserResolutions().add(userResolution);

        assertThat(user.getUserResolutions()).isEqualTo(userResolutionList);
    }

    @Test
    void testToString() {
        String userStr = "------------[USER]-------------\n" +
                " id : " + user.getIdUser() +
                " name : " + user.getName() +
                " surname : " + user.getSurname() +
                " email : " + user.getMail() +
                " password : " + user.getPassword();
        assertThat(user.toString()).isEqualTo(userStr);
    }

    @Test
    void login() {
        String passwordTrue = "jeanjacques";
        String passwordFalse = "false";

        assertThat(user.login(passwordTrue)).isTrue();
        assertThat(user.login(passwordFalse)).isFalse();
    }
}