package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public class RestaurantUpdateForm extends RestaurantForm {

    @NotNull
    // TODO: Add validation for own menuSections: check that list is permutation if menuSections' ids
    private List<URI> menuSectionsOrder;

    public List<URI> getMenuSectionsOrder() {
        return menuSectionsOrder;
    }

    public void setMenuSectionsOrder(List<URI> menuSectionsOrder) {
        this.menuSectionsOrder = menuSectionsOrder;
    }
}