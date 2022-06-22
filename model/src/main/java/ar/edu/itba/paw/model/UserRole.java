package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "account_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_role_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "account_role_id_seq", name = "account_role_id_seq")
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "role_to_authority",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<RoleAuthority> authorities = new HashSet<>();

    UserRole() {
    }

    @Deprecated
    /* Only for testing purposes */
    public UserRole(long id, String roleName, Collection<RoleAuthority> authorities) {
        this.id = id;
        this.roleName = roleName;
        this.authorities = new HashSet<>();
        this.authorities.addAll(authorities);
    }

    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<RoleAuthority> getAuthorities() {
        return this.authorities;
    }
}
