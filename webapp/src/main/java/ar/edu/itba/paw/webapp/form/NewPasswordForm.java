package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Password;
import javax.validation.constraints.NotNull;

public class NewPasswordForm {
    @Password
    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
