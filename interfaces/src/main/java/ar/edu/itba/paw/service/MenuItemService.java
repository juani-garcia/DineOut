package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuItem;

import java.util.List;

public interface MenuItemService {

    List<MenuItem> getBySectionId(long sectionId);

}
