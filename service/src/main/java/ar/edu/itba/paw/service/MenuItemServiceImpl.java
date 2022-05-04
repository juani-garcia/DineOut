package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.MenuItem;
import ar.edu.itba.paw.persistence.MenuItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private MenuItemDao menuItemDao;

    @Override
    public List<MenuItem> getBySectionId(long sectionId) {
        return menuItemDao.getBySectionId(sectionId);
    }

    @Override
    public MenuItem create(String name, String detail, double price, long sectionId, byte[] imageBytes) {
        Image image = null;
        if (imageBytes != null) {
            image = imageService.create(imageBytes);
        }
        return menuItemDao.create(name, detail, price, sectionId, (image != null) ? image.getId() : null);
    }

    @Override
    public boolean delete(long itemId) {
        return menuItemDao.delete(itemId);
    }

    @Override
    public boolean edit(long itemId, String name, String detail, double price, long sectionId, long ordering, byte[] imageBytes) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow( () -> new RuntimeException("Invalid itemId"));
        return menuItemDao.edit(itemId, name, detail, price, sectionId, ordering, menuItem.getImageId());
    }
}
