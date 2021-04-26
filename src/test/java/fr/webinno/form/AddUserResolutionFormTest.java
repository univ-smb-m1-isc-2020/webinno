package fr.webinno.form;

import fr.webinno.domain.Frequence;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddUserResolutionFormTest {
    AddUserResolutionForm userResolutionForm = new AddUserResolutionForm(10, 0, Frequence.MOIS, 5);

    @Test
    void getIdResolution() {
        assertThat(userResolutionForm.getIdResolution()).isEqualTo(10);
    }

    @Test
    void setIdResolution() {
        userResolutionForm.setIdResolution(10);
        assertThat(userResolutionForm.getIdResolution()).isEqualTo(10);

        userResolutionForm.setIdResolution(42);
        assertThat(userResolutionForm.getIdResolution()).isEqualTo(42);
    }

    @Test
    void getIdUser() {
        assertThat(userResolutionForm.getIdUser()).isEqualTo(0);
    }

    @Test
    void setIdUser() {
        userResolutionForm.setIdUser(0);
        assertThat(userResolutionForm.getIdUser()).isEqualTo(0);

        userResolutionForm.setIdUser(15);
        assertThat(userResolutionForm.getIdUser()).isEqualTo(15);
    }

    @Test
    void getFrequence() {
        assertThat(userResolutionForm.getFrequence()).isEqualTo(Frequence.MOIS);
    }

    @Test
    void setFrequence() {
        userResolutionForm.setFrequence(Frequence.MOIS);
        assertThat(userResolutionForm.getFrequence()).isEqualTo(Frequence.MOIS);

        userResolutionForm.setFrequence(Frequence.ANNEE);
        assertThat(userResolutionForm.getFrequence()).isEqualTo(Frequence.ANNEE);
    }

    @Test
    void getNb_occurences() {
        assertThat(userResolutionForm.getNb_occurences()).isEqualTo(5);
    }

    @Test
    void setNb_occurences() {
        userResolutionForm.setNb_occurences(5);
        assertThat(userResolutionForm.getNb_occurences()).isEqualTo(5);

        userResolutionForm.setNb_occurences(10);
        assertThat(userResolutionForm.getNb_occurences()).isEqualTo(10);
    }
}