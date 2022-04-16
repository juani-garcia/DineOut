package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuItem;
import ar.edu.itba.paw.persistence.MenuItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemDao menuItemDao;

    @Override
    public List<MenuItem> getBySectionId(long sectionId) {
        return menuItemDao.getBySectionId(sectionId);
    }
}
