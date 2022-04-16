package ar.edu.itba.paw.model;

public class MenuItem {

    private String name, detail;
    private double price;
    private long sectionId;

    public MenuItem(String name, String detail, double price, long sectionId) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.sectionId = sectionId;
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

    @Override
    public String toString() {
        return name + ' ' + detail + " $" + price;
    }
}
