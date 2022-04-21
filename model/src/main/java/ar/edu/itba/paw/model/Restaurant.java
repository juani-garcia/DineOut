package ar.edu.itba.paw.model;

public class Restaurant {
    private final long id, userID;
    private final String name, mail, address, detail;

    public Restaurant(long id, long userID, String name, String address, String mail, String detail) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.address = address;
        this.detail = detail;
        this.mail = mail;
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

//    public String getImage() {
//        return image;
//    }

    public long getUserID() {
        return userID;
    }

    public String getMail() {
        return mail;
    }
}
