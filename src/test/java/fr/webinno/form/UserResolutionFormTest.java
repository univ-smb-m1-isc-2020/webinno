package fr.webinno.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserResolutionFormTest {

    UserResolutionForm userResolutionForm = new UserResolutionForm(5,10);

    @Test
    void getIdUser() {
        assertThat(userResolutionForm.getIdUser()).isEqualTo(5);
    }

    @Test
    void setIdUser() {
        userResolutionForm.setIdUser(5);
        assertThat(userResolutionForm.getIdUser()).isEqualTo(5);

        userResolutionForm.setIdUser(15);
        assertThat(userResolutionForm.getIdUser()).isEqualTo(15);
    }

    @Test
    void getIdResolution() {
        assertThat(userResolutionForm.getIdResolution()).isEqualTo(10);
    }

    @Test
    void setIdResolution() {
        userResolutionForm.setIdResolution(10);
        assertThat(userResolutionForm.getIdResolution()).isEqualTo(10);

        userResolutionForm.setIdResolution(20);
        assertThat(userResolutionForm.getIdResolution()).isEqualTo(20);
    }
}