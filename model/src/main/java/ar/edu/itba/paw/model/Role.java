package ar.edu.itba.paw.model;

public enum Role {
    ADMIN("ADMIN", 1L),
    RESTAURANT("RESTAURANT", 2L),
    DINER("DINER", 3L),
    BASIC_USER("BASIC_USER", 4L);

    private final String roleName;
    private final Long id;

    Role(String roleName, Long id) {
        this.roleName = roleName;
        this.id = id;
    }

    @Override
    public String toString() {
        return roleName;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
