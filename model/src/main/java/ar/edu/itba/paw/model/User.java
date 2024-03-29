package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "account")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "account_id_seq", name = "account_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "locale")
    private Locale locale;

    // TODO: check EAGER fetch type
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_to_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Restaurant restaurant;

    protected User() {
    }

    public User(final String username, final String password, final String firstName, final String lastName, final Locale locale) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locale = locale;
    }

    @Deprecated
    /* Only for testing purposes */
    public User(final long id, final String username, final String password, final String firstName, final String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Collection<UserRole> getRoles() {
        return roles;
    }

    public Locale getLocale() {
        return locale;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void addRole(UserRole userRole) {
        this.roles.add(userRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ! (o instanceof User)) return false;
        User user = (User) o;
        return getId().longValue() == user.getId().longValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
