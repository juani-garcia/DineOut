package ar.edu.itba.paw.model;

public enum Category {
    ITALIAN("Italiana"),
    AMERICAN("Americana"),
    SPANISH("Española"),
    PIZZA("Pizza"),
    STEAKHOUSE("Parrilla"),
    BAR("Bar"),
    CHINESE("China"),
    VIET_THAI("Viet-Thai"),
    BURGERS("Hamburguesas"),
    JAPANESE("Japonesa"),
    SUSHI("Sushi"),
    VEGGIE("Veggie"),
    INDIAN("Hindú"),
    EMPANADAS("Empanadas"),
    BOULANGERIE("Panadería"),
    COFFEE("Café"),
    BREWERY("Cervecería"),
    PERUVIAN("Peruana"),
    NIKKEI("Nikkei"),
    ARABIAN("Árabe"),
    MEXICAN("Mexicana"),
    GELATO("Heladería");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return ordinal();
    }

    public static Category getByOrdinal(long ordinal) {
        for (Category c : Category.values()) {
            if (c.ordinal() == ordinal) {
                return c;
            }
        }
        return null;
    }

    public static Category getByName(String name) {
        for (Category c : Category.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

}
