package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MenuSectionUpdateForm extends MenuSectionForm{

    @NotNull
    // TODO: Add valid order in section list validation
    private Long ordering;

    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }
}
