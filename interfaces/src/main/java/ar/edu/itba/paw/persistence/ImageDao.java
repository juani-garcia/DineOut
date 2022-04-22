package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Image;

import java.util.Optional;

public interface ImageDao {

    Image create(final long id, final byte[] source);

    Optional<Image> getById(final long id);

    boolean edit(final long id, final byte[] source);

    boolean delete(final long id);

}
