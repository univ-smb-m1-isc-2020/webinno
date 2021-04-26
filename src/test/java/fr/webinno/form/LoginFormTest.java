package fr.webinno.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginFormTest {

    String[] rememberMe = {};
    LoginForm loginForm = new LoginForm("login", "pass", rememberMe);

    @Test
    void getRememberMe() {
        assertThat(loginForm.getRememberMe()).isEqualTo(rememberMe);
    }

    @Test
    void setRememberMe() {
        loginForm.setRememberMe(rememberMe);
        assertThat(loginForm.getRememberMe()).isEqualTo(rememberMe);

        String[] str = {"oui"};

        loginForm.setRememberMe(str);
        assertThat(loginForm.getRememberMe()).isEqualTo(str);
    }

    @Test
    void getUserName() {
        assertThat(loginForm.getUserName()).isEqualTo("login");
    }

    @Test
    void setUserName() {
        loginForm.setUserName("login");
        assertThat(loginForm.getUserName()).isEqualTo("login");

        loginForm.setUserName("log");
        assertThat(loginForm.getUserName()).isEqualTo("log");
    }

    @Test
    void getPassword() {
        assertThat(loginForm.getPassword()).isEqualTo("pass");
    }

    @Test
    void setPassword() {
        loginForm.setPassword("pass");
        assertThat(loginForm.getPassword()).isEqualTo("pass");

        loginForm.setPassword("password");
        assertThat(loginForm.getPassword()).isEqualTo("password");
    }
}