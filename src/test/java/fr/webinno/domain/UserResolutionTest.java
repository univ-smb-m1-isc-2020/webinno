package fr.webinno.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserResolutionTest {

    User user = new User("Jean", "Jacques", "jean.jacques@gmail.com", "jeanjacques");
    Resolution resolution = new Resolution("Test des classes");
    UserResolution userResolution = new UserResolution(Frequence.SEMAINE, 2, user, resolution);
    Historique historique = new Historique(new Date(), true, userResolution);

    @Test
    void getIdUserResolution() {
        assertThat(userResolution.getIdUserResolution()).isEqualTo(0);
    }

    @Test
    void getFrequence() {
        assertThat(userResolution.getFrequence()).isEqualTo(Frequence.SEMAINE);
    }

    @Test
    void setFrequence() {
        userResolution.setFrequence(Frequence.SEMAINE);

        assertThat(userResolution.getFrequence()).isEqualTo(Frequence.SEMAINE);

        userResolution.setFrequence(Frequence.ANNEE);

        assertThat(userResolution.getFrequence()).isEqualTo(Frequence.ANNEE);
    }

    @Test
    void getNbOccurence() {
        assertThat(userResolution.getNbOccurence()).isEqualTo(2);
    }

    @Test
    void setNbOccurence() {
        userResolution.setNbOccurence(2);

        assertThat(userResolution.getNbOccurence()).isEqualTo(2);

        userResolution.setNbOccurence(7);

        assertThat(userResolution.getNbOccurence()).isEqualTo(7);
    }

    @Test
    void getUser() {
        assertThat(userResolution.getUser()).isEqualTo(user);
    }

    @Test
    void setUser() {
        User u = new User("Luc", "Blanc", "lucblanc@gmail.com", "lucblanc");

        userResolution.setUser(user);
        assertThat(userResolution.getUser()).isEqualTo(user);

        userResolution.setUser(u);
        assertThat(userResolution.getUser()).isEqualTo(u);
    }

    @Test
    void getResolution() {
        assertThat(userResolution.getResolution()).isEqualTo(resolution);
    }

    @Test
    void setResolution() {
        Resolution r = new Resolution("Test setResolution");

        userResolution.setResolution(resolution);
        assertThat(userResolution.getResolution()).isEqualTo(resolution);

        userResolution.setResolution(r);
        assertThat(userResolution.getResolution()).isEqualTo(r);
    }

    @Test
    void getHistoriques() {
        assertThat(userResolution.getHistoriques()).isEqualTo(new ArrayList<Historique>());

        List<Historique> historiqueList = new ArrayList<Historique>();
        historiqueList.add(historique);

        userResolution.getHistoriques().add(historique);
        assertThat(userResolution.getHistoriques()).isEqualTo(historiqueList);
    }

    @Test
    void testToString() {
        String str = "User : " + userResolution.getUser().getName()+ ", Action : " + userResolution.getResolution().getAction() + ", Frequence : " + userResolution.getFrequence() + " Nb_occ : " + userResolution.getNbOccurence();
        assertThat(userResolution.toString()).isEqualTo(str);
    }

    @Test
    void dejaValideJour() {
        assertThat(userResolution.dejaValideJour()).isFalse();

        userResolution.getHistoriques().add(historique);

        assertThat(userResolution.dejaValideJour()).isTrue();
    }

    @Test
    void getHistoriqueLastSemaine() {
        // TODO:
    }

    @Test
    void updateHistorique() {
        // TODO:
    }
}