package ar.edu.itba.paw.model;

public class Image {

    private long id;
    private byte[] source;

    public Image(final long id, final byte[] source) {
        this.id  =id;
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }
}