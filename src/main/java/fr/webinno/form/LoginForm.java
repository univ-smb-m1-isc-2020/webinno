package fr.webinno.form;

public class LoginForm {
    private String userName;

    private String password;

    private String[] rememberMe;

    public String[] getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String[] rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
