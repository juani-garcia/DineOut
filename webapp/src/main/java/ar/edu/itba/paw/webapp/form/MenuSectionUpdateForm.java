package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public class MenuSectionUpdateForm extends MenuSectionForm {

    @NotNull
    // TODO: Add valid and all menuItems validation
    private List<URI> menuItemsOrder;

    public List<URI> getMenuItemsOrder() {
        return menuItemsOrder;
    }

    public void setMenuItemsOrder(List<URI> menuItemsOrder) {
        this.menuItemsOrder = menuItemsOrder;
    }
}
