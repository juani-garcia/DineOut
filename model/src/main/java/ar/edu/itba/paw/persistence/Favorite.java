package ar.edu.itba.paw.persistence;

public class Favorite {
    private final Long restaurantId, userId;

    protected Favorite(Long restaurantId, Long userId) {
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public Long getUserId() {
        return userId;
    }
}
