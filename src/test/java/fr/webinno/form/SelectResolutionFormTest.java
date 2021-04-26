package fr.webinno.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SelectResolutionFormTest {

    SelectResolutionForm selectResolutionForm = new SelectResolutionForm(5,10);

    @Test
    void getIdUser() {
        assertThat(selectResolutionForm.getIdUser()).isEqualTo(5);
    }

    @Test
    void setIdUser() {
        selectResolutionForm.setIdUser(5);
        assertThat(selectResolutionForm.getIdUser()).isEqualTo(5);

        selectResolutionForm.setIdUser(15);
        assertThat(selectResolutionForm.getIdUser()).isEqualTo(15);
    }

    @Test
    void getIdResolution() {
        assertThat(selectResolutionForm.getIdResolution()).isEqualTo(10);
    }

    @Test
    void setIdResolution() {
        selectResolutionForm.setIdResolution(10);
        assertThat(selectResolutionForm.getIdResolution()).isEqualTo(10);

        selectResolutionForm.setIdResolution(20);
        assertThat(selectResolutionForm.getIdResolution()).isEqualTo(20);
    }
}