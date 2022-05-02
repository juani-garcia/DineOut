package ar.edu.itba.paw.persistence;

public class RoleToAuthority {
    public final long id, authorityId, roleId;

    protected RoleToAuthority(long id, long authorityId, long roleId) {
        this.id = id;
        this.authorityId = authorityId;
        this.roleId = roleId;
    }

    public long getId() {
        return id;
    }

    public long getAuthorityId() {
        return authorityId;
    }

    public long getRoleId() {
        return roleId;
    }
}
