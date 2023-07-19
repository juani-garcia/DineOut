package ar.edu.itba.paw.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public enum Shift {
    MORNING("shift.morning.name", LocalTime.of(8, 0), LocalTime.of(11, 59)),
    NOON("shift.noon.name", LocalTime.of(12, 0), LocalTime.of(15, 59)),
    AFTERNOON("shift.afternoon.name", LocalTime.of(16, 0), LocalTime.of(19, 59)),
    EVENING("shift.evening.name", LocalTime.of(20, 0), LocalTime.of(23, 59));

    private final String message;
    private final LocalTime start, end;

    Shift(String message, LocalTime start, LocalTime end) {
        this.message = message;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public long getId() {
        return ordinal();
    }

    public String getIdString() {
        return String.valueOf(getId());
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

    public static Shift getByMessage(String message) {
        for (Shift s : Shift.values()) {
            if (Objects.equals(s.message, message)) {
                return s;
            }
        }
        return null;
    }

    private boolean belongs(LocalTime time) {
        return (time.isAfter(start) || time.equals(start)) &&
                (time.isBefore(end) || time.equals(end));
    }

    public static boolean belongs(Collection<Shift> hours, LocalTime time) {
        if (hours.isEmpty()) return true;

        for (Shift hour : hours) {
            if (hour.belongs(time)) {
                return true;
            }
        }
        return false;
    }

    public static List<LocalTime> availableTimes(Collection<Shift> shifts, long step) {
        List<LocalTime> ans = new LinkedList<>();
        if(shifts.isEmpty()) shifts.addAll(Arrays.stream(Shift.values()).collect(Collectors.toList()));
        for(Shift shift : shifts) {
            ans.addAll(availableTimes(shift, step));
        }
        return ans.stream().sorted().collect(Collectors.toList());
    }

    public static List<LocalTime> availableTimes(Shift shift, long step) {
        List<LocalTime> ans = new LinkedList<>();
        LocalTime it = shift.getStart();
        while (it.isBefore(shift.getEnd()) || it.equals(shift.getEnd())) {
            ans.add(it);
            LocalTime next = it.plus(step, ChronoUnit.MINUTES);
            if (next.isBefore(shift.getStart())) break;
            it = next;
        }
        return ans;
    }
}
