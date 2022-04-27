package ar.edu.itba.paw.model;

public class Restaurant {
    private final long id, userID;
    private final String name, mail, address, detail;
    private final Zone zone;

    public Restaurant(long id, long userID, String name, String address, String mail, String detail, Zone zone) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.address = address;
        this.detail = detail;
        this.mail = mail;
        this.zone = zone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDetail() {
        return detail;
    }

    public Zone getZone() {
        return zone;
    }

    public long getUserID() {
        return userID;
    }

    public String getMail() {
        return mail;
    }
}
