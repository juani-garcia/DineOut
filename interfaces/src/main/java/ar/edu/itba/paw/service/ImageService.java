package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;

import java.util.Optional;

public interface ImageService {

    Image create(final byte[] source);

    Optional<Image> getById(final long id);

    void edit(final long id, final byte[] source);

    void delete(final long id);

}
