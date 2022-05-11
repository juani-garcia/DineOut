package ar.edu.itba.paw.model;

public class UserRole {
    private final long id;
    private final String roleName;

    public UserRole(long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
