package fr.webinno.form;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateAccountFormTest {

    CreateAccountForm createAccountForm = new CreateAccountForm("Jean","Jacques","jean.jacques@gmail.com","jeanjacques");

    @Test
    void getNom() {
        assertThat(createAccountForm.getNom()).isEqualTo("Jean");
    }

    @Test
    void setNom() {
        createAccountForm.setNom("Jean");
        assertThat(createAccountForm.getNom()).isEqualTo("Jean");

        createAccountForm.setNom("Luc");
        assertThat(createAccountForm.getNom()).isEqualTo("Luc");
    }

    @Test
    void getPrenom() {
        assertThat(createAccountForm.getPrenom()).isEqualTo("Jacques");
    }

    @Test
    void setPrenom() {
        createAccountForm.setPrenom("Jacques");
        assertThat(createAccountForm.getPrenom()).isEqualTo("Jacques");

        createAccountForm.setPrenom("Blanc");
        assertThat(createAccountForm.getPrenom()).isEqualTo("Blanc");
    }

    @Test
    void getMail() {
        assertThat(createAccountForm.getMail()).isEqualTo("jean.jacques@gmail.com");
    }

    @Test
    void setMail() {
        createAccountForm.setMail("jean.jacques@gmail.com");
        assertThat(createAccountForm.getMail()).isEqualTo("jean.jacques@gmail.com");

        createAccountForm.setMail("luc.blanc@gmail.com");
        assertThat(createAccountForm.getMail()).isEqualTo("luc.blanc@gmail.com");
    }

    @Test
    void getPass() {
        assertThat(createAccountForm.getPass()).isEqualTo("jeanjacques");
    }

    @Test
    void setPass() {
        createAccountForm.setPass("jeanjacques");
        assertThat(createAccountForm.getPass()).isEqualTo("jeanjacques");

        createAccountForm.setPass("lucblanc");
        assertThat(createAccountForm.getPass()).isEqualTo("lucblanc");
    }
}