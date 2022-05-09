package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.FieldsValueMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmPassword"
)
public class NewPasswordForm {
    @Size(min = 8)
    private String password;

    @NotNull
    private String confirmPassword;

    private  String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
