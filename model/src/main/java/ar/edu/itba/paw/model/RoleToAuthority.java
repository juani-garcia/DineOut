package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "role_to_authority")
public class RoleToAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_to_authority_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "role_to_authority_id_seq", name = "role_to_authority_id_seq")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "authority_id", nullable = false)
    private RoleAuthority roleAuthority;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private UserRole userRole;

    RoleToAuthority() {
    }

    @Deprecated
    public RoleToAuthority(long id, long authorityId, long roleId) {
        this.id = id;
        // this.authorityId = authorityId;
        // this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public RoleAuthority getAuthority() {
        return roleAuthority;
    }

    public UserRole getRoleId() {
        return userRole;
    }

}
