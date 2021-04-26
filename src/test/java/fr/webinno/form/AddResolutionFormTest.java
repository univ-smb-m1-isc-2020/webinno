package fr.webinno.form;

import fr.webinno.domain.Frequence;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddResolutionFormTest {

    AddResolutionForm addResolutionForm = new AddResolutionForm(10, 0, Frequence.MOIS, 5, "Test des classes");

    @Test
    void getIdResolution() {
        assertThat(addResolutionForm.getIdResolution()).isEqualTo(10);
    }

    @Test
    void getIdUser() {
        assertThat(addResolutionForm.getIdUser()).isEqualTo(0);
    }

    @Test
    void getFrequence() {
        assertThat(addResolutionForm.getFrequence()).isEqualTo(Frequence.MOIS);
    }

    @Test
    void getNb_occurences() {
        assertThat(addResolutionForm.getNb_occurences()).isEqualTo(5);
    }

    @Test
    void getAction() {
        assertThat(addResolutionForm.getAction()).isEqualTo("Test des classes");
    }

    @Test
    void setIdResolution() {
        addResolutionForm.setIdResolution(10);
        assertThat(addResolutionForm.getIdResolution()).isEqualTo(10);

        addResolutionForm.setIdResolution(42);
        assertThat(addResolutionForm.getIdResolution()).isEqualTo(42);
    }

    @Test
    void setIdUser() {
        addResolutionForm.setIdUser(0);
        assertThat(addResolutionForm.getIdUser()).isEqualTo(0);

        addResolutionForm.setIdUser(15);
        assertThat(addResolutionForm.getIdUser()).isEqualTo(15);
    }

    @Test
    void setFrequence() {
        addResolutionForm.setFrequence(Frequence.MOIS);
        assertThat(addResolutionForm.getFrequence()).isEqualTo(Frequence.MOIS);        assertThat(addResolutionForm.getFrequence()).isEqualTo(Frequence.MOIS);

        addResolutionForm.setFrequence(Frequence.ANNEE);
        assertThat(addResolutionForm.getFrequence()).isEqualTo(Frequence.ANNEE);
    }

    @Test
    void setNb_occurences() {
        addResolutionForm.setNb_occurences(5);
        assertThat(addResolutionForm.getNb_occurences()).isEqualTo(5);

        addResolutionForm.setNb_occurences(10);
        assertThat(addResolutionForm.getNb_occurences()).isEqualTo(10);
    }

    @Test
    void setAction() {
        addResolutionForm.setAction("Test des classes");
        assertThat(addResolutionForm.getAction()).isEqualTo("Test des classes");

        addResolutionForm.setAction("Test");
        assertThat(addResolutionForm.getAction()).isEqualTo("Test");
    }
}