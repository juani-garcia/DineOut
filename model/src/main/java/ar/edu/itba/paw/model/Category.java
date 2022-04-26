package ar.edu.itba.paw.model;

public enum Category {
    ITALIAN     ("categories.name.italian"),
    AMERICAN    ("categories.name.american"),
    SPANISH     ("categories.name.spanish"),
    PIZZA       ("categories.name.pizza"),
    STEAKHOUSE  ("categories.name.steakhouse"),
    BAR         ("categories.name.bar"),
    CHINESE     ("categories.name.chinese"),
    VIET_THAI   ("categories.name.viet_thai"),
    BURGERS     ("categories.name.burgers"),
    JAPANESE    ("categories.name.japanese"),
    SUSHI       ("categories.name.sushi"),
    VEGGIE      ("categories.name.veggie"),
    INDIAN      ("categories.name.indian"),
    EMPANADAS   ("categories.name.empanadas"),
    BOULANGERIE ("categories.name.boulangerie"),
    COFFEE      ("categories.name.coffee"),
    BREWERY     ("categories.name.brewery"),
    PERUVIAN    ("categories.name.peruvian"),
    NIKKEI      ("categories.name.nikkei"),
    ARABIAN     ("categories.name.arabian"),
    MEXICAN     ("categories.name.mexican"),
    GELATO      ("categories.name.gelato");

    private final String message;

    Category(String name) {
        this.message = name;
    }

    public String getMessage() {
        return message;
    }

    public static Category getByOrdinal(long ordinal) {
        for(Category c : Category.values()) {
            if(c.ordinal() == ordinal) {
                return c;
            }
        }
        return null;
    }

}
