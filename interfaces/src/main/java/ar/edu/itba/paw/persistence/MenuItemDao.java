package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuItem;

import java.util.List;

public interface MenuItemDao {


    List<MenuItem> getBySectionId(long sectionId);
}
