package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Image;

import java.util.Optional;

public interface ImageDao {

    Image create(final byte[] source);

    Optional<Image> getById(final long id);

    void delete(final long id);

}
