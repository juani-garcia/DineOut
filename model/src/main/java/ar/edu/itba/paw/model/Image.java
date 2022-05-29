package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "image_id_seq", name = "image_id_seq")
    private Long id;

    @Column(nullable = false)
    private byte[] source;

    protected Image() {
    }

    public Image(final byte[] source) {
        this.source = source;
    }

    @Deprecated
    public Image(final long id, final byte[] source) {
        this.id = id;
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }
}