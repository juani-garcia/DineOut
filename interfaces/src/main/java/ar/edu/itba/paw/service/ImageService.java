package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Image;

import java.util.Optional;

public interface ImageService {

    Image create(final byte[] source);

    Optional<Image> getById(final long id);

    boolean edit(final long id, final byte[] source);

    boolean delete(final long id);

}
