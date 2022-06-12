package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteId implements Serializable {

    @Column(name = "restaurant_id")
    private Long restaurantId;


    @Column(name = "user_id")
    private Long userId;

    public FavoriteId() {
    }

    public FavoriteId(Long restaurantId, Long userId) {
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, userId);
    }
}
