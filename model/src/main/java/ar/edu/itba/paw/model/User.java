package ar.edu.itba.paw.model;

public class User {

    private final long id;
    private final String username, password;

    public User(final long id, final String username, final String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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
