package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MenuSectionUpdateForm extends MenuSectionForm {

    @NotNull
    // TODO: Add valid and all menuItems validation
    private List<Long> menuItemsOrder;

    public List<Long> getMenuItemsOrder() {
        return menuItemsOrder;
    }

    public void setMenuItemsOrder(List<Long> menuItemsOrder) {
        this.menuItemsOrder = menuItemsOrder;
    }
}
