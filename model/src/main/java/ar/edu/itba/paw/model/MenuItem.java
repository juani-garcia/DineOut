package ar.edu.itba.paw.model;

public class MenuItem {

    private String name, detail;
    private double price;
    private long sectionId, id, ordering;
    private long imageId;

    public MenuItem(long id, String name, String detail, double price, long sectionId, long ordering, Long imageId) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.sectionId = sectionId;
        this.ordering = ordering;
        this.imageId = imageId;
    }

    public long getOrdering() {
        return ordering;
    }

    public void setOrdering(long ordering) {
        this.ordering = ordering;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return name + ' ' + detail + " $" + price;
    }
}
