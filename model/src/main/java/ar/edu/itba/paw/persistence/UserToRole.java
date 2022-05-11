package ar.edu.itba.paw.persistence;

public class UserToRole {
    private final long id, roleId, userId;

    protected UserToRole(long id, long userId, long roleId) {
        this.id = id;
        this.roleId = roleId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getRoleId() {
        return roleId;
    }

    public long getUserId() {
        return userId;
    }
}
