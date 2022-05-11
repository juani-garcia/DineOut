package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(final ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Image create(final byte[] source) {
        return imageDao.create(source);
    }

    @Override
    public Optional<Image> getById(final long id) {
        return imageDao.getById(id);
    }

    @Override
    public boolean edit(final long id, final byte[] source) {
        return imageDao.edit(id, source);
    }

    @Override
    public boolean delete(final long id) {
        return imageDao.delete(id);
    }

}
