package ar.edu.itba.paw.model;

import java.time.LocalTime;
import java.util.List;

public enum Shift {
    MORNING("opening_hours.name.morning", LocalTime.of(8, 0), LocalTime.of(11, 59)),
    NOON("opening_hours.name.noon", LocalTime.of(12, 0), LocalTime.of(15, 59)),
    AFTERNOON("opening_hours.name.afternoon", LocalTime.of(16, 0), LocalTime.of(19, 59)),
    EVENING("opening_hours.name.evening", LocalTime.of(20, 0), LocalTime.of(23, 59));

    private final String message;
    private final LocalTime start, end;

    Shift(String message, LocalTime start, LocalTime end) {
        this.message = message;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return ordinal();
    }

    public String getMessage() {
        return message;
    }

    public static Shift getById(long id) {
        for (Shift hours : Shift.values()) {
            if (hours.getId() == id) {
                return hours;
            }
        }
        return null;
    }

    private boolean belongs(LocalTime time) {
        return (time.isAfter(start) || time.equals(start)) &&
                (time.isBefore(end) || time.equals(end));
    }

    public static boolean belongs(List<Shift> hours, LocalTime time) {
        for (Shift hour : hours) {
            if(hour.belongs(time)){
                return true;
            }
        }
        return false;
    }
}
