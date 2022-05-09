package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.UsernameExists;

import javax.validation.constraints.NotNull;

public class PasswordRecoveryForm {

    @NotNull
    @UsernameExists
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
