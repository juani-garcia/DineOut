package ar.edu.itba.paw.model;

public class RoleAuthority {
    private final long id;
    private final String authorityName;

    public RoleAuthority(long id, String authorityName) {
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
