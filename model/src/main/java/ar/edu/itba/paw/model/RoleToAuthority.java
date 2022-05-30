package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "role_to_authority")
public class RoleToAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_to_authority_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "role_to_authority_id_seq", name = "role_to_authority_id_seq")
    private Long id;

    @Column(name = "authority_id", nullable = false)
    private Long authorityId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    RoleToAuthority() {
    }

    @Deprecated
    public RoleToAuthority(long id, long authorityId, long roleId) {
        this.id = id;
        this.authorityId = authorityId;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public Long getRoleId() {
        return roleId;
    }

}
