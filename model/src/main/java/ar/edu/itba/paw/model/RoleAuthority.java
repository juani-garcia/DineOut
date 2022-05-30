package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "role_authorities")
public class RoleAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_authorities_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "role_authorities_id_seq", name = "role_authorities_id_seq")
    private long id;

    @Column(name = "authority_name", nullable = false)
    private String authorityName;

    RoleAuthority() {
    }

    @Deprecated
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
