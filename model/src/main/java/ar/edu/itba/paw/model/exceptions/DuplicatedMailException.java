package ar.edu.itba.paw.model.exceptions;

import ar.edu.itba.paw.model.Zone;

import java.util.List;

public class DuplicatedMailException extends RuntimeException {

    private static final String MESSAGE = "The mail is already in use";
    private long userID;
    private String name, address, mail, detail;
    private Zone zone;
    private List<Long> categories;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
