package fr.webinno.form;

import fr.webinno.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserFormTest {

    UserForm userForm = new UserForm(5);

    @Test
    void getIdUser() {
        assertThat(userForm.getIdUser()).isEqualTo(5);
    }

    @Test
    void setIdUser() {
        userForm.setIdUser(5);
        assertThat(userForm.getIdUser()).isEqualTo(5);

        userForm.setIdUser(15);
        assertThat(userForm.getIdUser()).isEqualTo(15);
    }
}