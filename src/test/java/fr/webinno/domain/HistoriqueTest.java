package fr.webinno.domain;

import fr.webinno.service.HistoriqueService;
import fr.webinno.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HistoriqueTest {
    User user = new User("Jean", "Jacques", "jean.jacques@gmail.com", "jeanjacques");
    Resolution resolution = new Resolution("Test des classes");
    UserResolution userResolution = new UserResolution(Frequence.SEMAINE, 2, user, resolution);
    Historique historique = new Historique(new Date(), true, userResolution);

    @Test
    void testToString() {

        String tostr = "-> id: 0, date : " + historique.getDate() + ", fait : true";
        assertThat(historique.toString()).isEqualTo(tostr);
    }

    @Test
    void sameDate() {
        Date dFalse = new Date(22/04/2021);
        assertThat(historique.sameDate(dFalse)).isFalse();
        Date dTrue = new Date();
        assertThat(historique.sameDate(dTrue)).isTrue();
    }

    @Test
    void testEquals() {
        Historique historiqueTrue = historique;
        Historique historiqueFalse = new Historique(new Date(20/04/2021),false,userResolution);

        assertThat(historique.equals(historiqueFalse)).isFalse();
        assertThat(historique.equals(historiqueTrue)).isTrue();
    }

    @Test
    void getIdHistorique() {
        assertThat(historique.getIdHistorique()).isEqualTo(0);
    }

    @Test
    void getDate() {
        Date d = new Date();
        assertThat(historique.getDate()).isEqualToIgnoringHours(d);
    }

    @Test
    void setDate() {
        Date d = new Date(30/05/2020);
        assertThat(historique.getDate()).isNotEqualTo(d);
        historique.setDate(d);
        assertThat(historique.getDate()).isEqualToIgnoringHours(d);
    }

    @Test
    void isDone() {
        Historique historiqueTrue = historique;
        Historique historiqueFalse = new Historique(historique.getDate(),false,userResolution);
        assertThat(historiqueTrue.isDone()).isTrue();
        assertThat(historiqueFalse.isDone()).isFalse();
    }

    @Test
    void setDone() {
        historique.setDone(true);
        assertThat(historique.isDone()).isTrue();
        historique.setDone(false);
        assertThat(historique.isDone()).isFalse();
    }

    @Test
    void getUserResolution() {
        assertThat(historique.getUserResolution()).isEqualTo(userResolution);
    }

}