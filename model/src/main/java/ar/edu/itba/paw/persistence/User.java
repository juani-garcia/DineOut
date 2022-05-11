package ar.edu.itba.paw.persistence;

public class User {
    private final long id;
    private final String username, password, firstName, lastName;

    protected User(final long id, final String username, final String password, final String firstName, final String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
