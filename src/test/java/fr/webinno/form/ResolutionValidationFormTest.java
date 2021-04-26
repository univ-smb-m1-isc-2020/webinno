package fr.webinno.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResolutionValidationFormTest {

    ResolutionValidationForm resolutionValidationForm = new ResolutionValidationForm(5,10);

    @Test
    void getIdUser() {
        assertThat(resolutionValidationForm.getIdUser()).isEqualTo(5);
    }

    @Test
    void setIdUser() {
        resolutionValidationForm.setIdUser(5);
        assertThat(resolutionValidationForm.getIdUser()).isEqualTo(5);

        resolutionValidationForm.setIdUser(15);
        assertThat(resolutionValidationForm.getIdUser()).isEqualTo(15);
    }

    @Test
    void getIdHistorique() {
        assertThat(resolutionValidationForm.getIdHistorique()).isEqualTo(10);
    }

    @Test
    void setIdHistorique() {
        resolutionValidationForm.setIdHistorique(10);
        assertThat(resolutionValidationForm.getIdHistorique()).isEqualTo(10);

        resolutionValidationForm.setIdHistorique(20);
        assertThat(resolutionValidationForm.getIdHistorique()).isEqualTo(20);
    }
}