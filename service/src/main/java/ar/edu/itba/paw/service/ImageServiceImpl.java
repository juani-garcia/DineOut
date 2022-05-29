package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
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
    public void edit(final long id, final byte[] source) {
        Image image = getById(id).orElseThrow(NotFoundException::new);
        image.setSource(source);
    }

    @Override
    public void delete(final long id) {
        imageDao.delete(id);
    }

}
