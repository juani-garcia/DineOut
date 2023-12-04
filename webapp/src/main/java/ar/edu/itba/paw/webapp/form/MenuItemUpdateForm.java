package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.FileSize;
import ar.edu.itba.paw.webapp.validations.FileType;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MenuItemUpdateForm extends MenuItemForm {

    @NotNull
    // TODO: Add ordering validation
    private Long ordering;

    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }
}
