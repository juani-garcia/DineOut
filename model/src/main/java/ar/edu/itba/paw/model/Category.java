package ar.edu.itba.paw.model;

public enum Category {
    ITALIAN     (1, "categories.name.italian"),
    AMERICAN    (2, "categories.name.american"),
    SPANISH     (3, "categories.name.spanish"),
    PIZZA       (4, "categories.name.pizza"),
    STEAKHOUSE  (5, "categories.name.steakhouse"),
    BAR         (6, "categories.name.bar"),
    CHINESE     (7, "categories.name.chinese"),
    VIET_THAI   (8, "categories.name.viet_thai"),
    BURGERS     (9, "categories.name.burgers"),
    JAPANESE    (10, "categories.name.japanese"),
    SUSHI       (11, "categories.name.sushi"),
    VEGGIE      (12, "categories.name.veggie"),
    INDIAN      (13, "categories.name.indian"),
    EMPANADAS   (14, "categories.name.empanadas"),
    BOULANGERIE (15, "categories.name.boulangerie"),
    COFFEE      (16, "categories.name.coffee"),
    BREWERY     (17, "categories.name.brewery"),
    PERUVIAN    (18, "categories.name.peruvian"),
    NIKKEI      (19, "categories.name.nikkei"),
    ARABIAN     (20, "categories.name.arabian"),
    MEXICAN     (21, "categories.name.mexican"),
    GELATO      (22, "categories.name.gelato");

    private final String message;
    private final long id;

    Category(long id, String name) {
        this.message = name;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public static Category getById(long id) {
        for(Category c : Category.values()) {
            if(c.getId() == id) {
                return c;
            }
        }
        return null;
    }

}
