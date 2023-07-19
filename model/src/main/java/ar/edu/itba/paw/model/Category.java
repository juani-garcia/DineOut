package ar.edu.itba.paw.model;

import java.util.Objects;

public enum Category {
    ITALIAN("category.italian.name"),
    AMERICAN("category.american.name"),
    SPANISH("category.spanish.name"),
    PIZZA("category.pizza.name"),
    STEAKHOUSE("category.steakhouse.name"),
    BAR("category.bar.name"),
    CHINESE("category.chinese.name"),
    VIET_THAI("category.viet_thai.name"),
    BURGERS("category.burgers.name"),
    JAPANESE("category.japanese.name"),
    SUSHI("category.sushi.name"),
    VEGGIE("category.veggie.name"),
    INDIAN("category.indian.name"),
    EMPANADAS("category.empanadas.name"),
    BOULANGERIE("category.boulangerie.name"),
    COFFEE("category.coffee.name"),
    BREWERY("category.brewery.name"),
    PERUVIAN("category.peruvian.name"),
    NIKKEI("category.nikkei.name"),
    ARABIAN("category.arabian.name"),
    MEXICAN("category.mexican.name"),
    GELATO("category.gelato.name");

    private final String message;

    Category(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return ordinal();
    }

    public String getIdString() {
        return String.valueOf(getId());
    }

    public static Category getById(long id) {
        for (Category c : Category.values()) {
            if (c.ordinal() == id) {
                return c;
            }
        }
        return null;
    }

    public static Category getByMessage(String message) {
        for (Category c : Category.values()) {
            if (Objects.equals(c.message, message)) {
                return c;
            }
        }
        return null;
    }

}
