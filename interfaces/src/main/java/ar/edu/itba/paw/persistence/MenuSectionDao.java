package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuSection;

import java.util.List;
import java.util.Optional;

public interface MenuSectionDao {

    Optional<MenuSection> getById(final long sectionId);

    void delete(long sectionId);

}
