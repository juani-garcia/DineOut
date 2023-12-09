package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class MenuSectionUpdateForm extends MenuSectionForm {

    @NotNull
    // TODO: Add valid and all menuItems validation
    private List<Long> menuItems;

    public List<Long> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Long> menuItems) {
        this.menuItems = menuItems;
    }
}
