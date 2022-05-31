package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "account")
public class User {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_to_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<UserRole> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private Collection<Restaurant> favorites;

    protected User() {
    }

    public User(final String username, final String password, final String firstName, final String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(UserRole userRole) {
        this.roles.add(userRole);
    }

    public void addFavorite(Restaurant restaurant) {
        this.favorites.add(restaurant);
    }

    public Collection<Restaurant> getFavorites() {
        return favorites;
    }

    public void removeFavorite(Restaurant restaurant) {
        this.favorites.remove(restaurant);
    }

}
