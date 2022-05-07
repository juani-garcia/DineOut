package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.DuplicatedUsername;
import ar.edu.itba.paw.webapp.validations.FieldsValueMatch;
import ar.edu.itba.paw.webapp.validations.Password;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmPassword"
)
public class UserForm {

    @DuplicatedUsername
    @Pattern(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
    private String username;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String firstName, lastName;

    @Password
    private String password;

    @NotNull
    private String confirmPassword;

    private Boolean isRestaurant = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getIsRestaurant() {
        return isRestaurant;
    }

    public void setIsRestaurant(Boolean isRestaurant) {
        this.isRestaurant = isRestaurant;
    }
}
