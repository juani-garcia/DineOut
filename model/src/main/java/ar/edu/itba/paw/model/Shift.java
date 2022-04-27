package ar.edu.itba.paw.model;

import java.time.LocalTime;
import java.util.List;

public enum Shift {
    MORNING("Mañana", LocalTime.of(8, 0), LocalTime.of(11, 59)),
    NOON("Mediodiía", LocalTime.of(12, 0), LocalTime.of(15, 59)),
    AFTERNOON("Tarde", LocalTime.of(16, 0), LocalTime.of(19, 59)),
    EVENING("Noche", LocalTime.of(20, 0), LocalTime.of(23, 59));

    private final String name;
    private final LocalTime start, end;

    Shift(String name, LocalTime start, LocalTime end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return ordinal();
    }

    public String getName() {
        return name;
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

    public static Shift getByName(String name) {
        for(Shift shift : Shift.values()) {
            if (shift.getName().equals((name))) {
                return shift;
            }
        }
        return null;
    }
}
