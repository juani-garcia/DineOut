package ar.edu.itba.paw.model;

public class Favorite {
    private final Long restaurantId, userId;

    public Favorite(Long restaurantId, Long userId) {
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
