package ar.edu.itba.paw.model;

public enum OpeningHours {
    MORNING     (1, "opening_hours.name.morning"),
    NOON        (2, "opening_hours.name.noon"),
    AFTERNOON   (3, "opening_hours.name.afternoon"),
    EVENING     (4, "opening_hours.name.evening");

    private final long id;
    private final String message;

    OpeningHours(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public static OpeningHours getById(long id) {
        for(OpeningHours hours : OpeningHours.values()) {
            if(hours.getId() == id) {
                return hours;
            }
        }
        return null;
    }
}
