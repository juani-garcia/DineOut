package ar.edu.itba.paw.model;

public class Restaurant {
    private final long id;
    private final String name, address, detail, image;

    public Restaurant(long id, String name, String address, String detail, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.detail = detail;
        this.image = image;
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

    public String getImage() {
        return image;
    }
}
