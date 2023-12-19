package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RestaurantUpdateForm extends RestaurantForm {

    @NotNull
    // TODO: Consider if this should be list of URNs and then we parse
    // TODO: Add validation for own menuSections: check that list is permutation if menuSections' ids
    private List<Long> menuSectionsOrder;

    public List<Long> getMenuSectionsOrder() {
        return menuSectionsOrder;
    }

    public void setMenuSectionsOrder(List<Long> menuSectionsOrder) {
        this.menuSectionsOrder = menuSectionsOrder;
    }
}