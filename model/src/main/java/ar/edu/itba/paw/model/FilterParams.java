package ar.edu.itba.paw.model;

import java.util.Optional;

public class FilterParams {
    private int page = 1;
    private String match;
    private Zone zone;
    private Category category;
    private Shift shift;
    private Long favoriteOf, recommendedFor;

    public String getMatch() {
        return match;
    }

    public FilterParams setMatch(String match) {
        this.match = match;
        return this;
    }

    public Zone getZone() {
        return zone;
    }

    public FilterParams setZone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public FilterParams setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Shift getShift() {
        return shift;
    }

    public FilterParams setShift(Shift shift) {
        this.shift = shift;
        return this;
    }

    public Long getFavoriteOf() {
        return favoriteOf;
    }

    public FilterParams setFavoriteOf(Long favoriteOf) {
        this.favoriteOf = favoriteOf;
        return this;
    }

    public Long getRecommendedFor() {
        return recommendedFor;
    }

    public FilterParams setRecommendedFor(Long recommendedFor) {
        this.recommendedFor = recommendedFor;
        return this;
    }

    public int getPage() {
        return page;
    }

    public FilterParams setPage(int page) {
        this.page = page;
        return this;
    }
}
