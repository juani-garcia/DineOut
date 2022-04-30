package ar.edu.itba.paw.persistence;

public class UserRole {
    private final long id;
    private final String roleName;

    protected UserRole(long id, String roleName) {
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
