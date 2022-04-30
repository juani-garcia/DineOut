package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.MenuItem;

import java.util.List;

public interface MenuItemService {

    List<MenuItem> getBySectionId(long sectionId);

    MenuItem create(String name, String detail, double price, long sectionId, long ordering, Long imageId);

    boolean delete(long itemId);

    boolean edit(long itemId, String name, String detail, double price, long sectionId, long ordering, Long imageId);
}
