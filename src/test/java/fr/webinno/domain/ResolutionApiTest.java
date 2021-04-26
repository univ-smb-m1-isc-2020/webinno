package fr.webinno.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResolutionApiTest {

    ResolutionApi resolution = new ResolutionApi(0L,"Test des classes",Frequence.SEMAINE,5);

    @Test
    void getIdResolution() {
        assertThat(resolution.getIdResolution()).isEqualTo(0);
    }

    @Test
    void setIdResolution() {
        resolution.setIdResolution(0L);

        assertThat(resolution.getIdResolution()).isEqualTo(0);

        resolution.setIdResolution(1000L);

        assertThat(resolution.getIdResolution()).isEqualTo(1000);
    }

    @Test
    void getAction() {
        assertThat(resolution.getAction()).isEqualTo("Test des classes");
    }

    @Test
    void setAction() {
        resolution.setAction("Test des classes");

        assertThat(resolution.getAction()).isEqualTo("Test des classes");

        resolution.setAction("Test");

        assertThat(resolution.getAction()).isEqualTo("Test");
    }

    @Test
    void getFrequence() {
        assertThat(resolution.getFrequence()).isEqualTo(Frequence.SEMAINE);
    }

    @Test
    void setFrequence() {
        resolution.setFrequence(Frequence.SEMAINE);

        assertThat(resolution.getFrequence()).isEqualTo(Frequence.SEMAINE);

        resolution.setFrequence(Frequence.MOIS);

        assertThat(resolution.getFrequence()).isEqualTo(Frequence.MOIS);
    }

    @Test
    void getNbOccurence() {
        assertThat(resolution.getNbOccurence()).isEqualTo(5);
    }

    @Test
    void setNbOccurence() {
        resolution.setNbOccurence(5);

        assertThat(resolution.getNbOccurence()).isEqualTo(5);

        resolution.setNbOccurence(1);

        assertThat(resolution.getNbOccurence()).isEqualTo(1);
    }
}