package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.Zone;

public class Restaurant {
    private final Long id, userID, imageId, favCount;
    private final String name, mail, address, detail;
    private final Zone zone;

    public Restaurant(long id, Long userID, String name, Long imageId, String address, String mail, String detail, Zone zone, Long favCount) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.imageId = imageId;
        this.address = address;
        this.detail = detail;
        this.mail = mail;
        this.zone = zone;
        this.favCount = favCount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getImageId() {
        return imageId;
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

    public Long getFavCount() {
        return favCount;
    }
}
