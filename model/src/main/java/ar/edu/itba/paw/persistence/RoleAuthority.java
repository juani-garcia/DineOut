package ar.edu.itba.paw.persistence;

public class RoleAuthority {
    private final long id;
    private final String authorityName;

    protected RoleAuthority(long id, String authorityName) {
        this.id = id;
        this.authorityName = authorityName;
    }

    public long getId() {
        return id;
    }

    public String getAuthorityName() {
        return authorityName;
    }
}
