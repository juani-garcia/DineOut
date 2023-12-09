package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.webapp.validations.DuplicatedMail;
import ar.edu.itba.paw.webapp.validations.FileSize;
import ar.edu.itba.paw.webapp.validations.FileType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class RestaurantUpdateForm extends RestaurantForm {

    @NotNull
    // TODO: Add validation for own menuSections: check that list is permutation if menuSections' ids
    private List<Long> menuSectionsOrder;

    public List<Long> getMenuSectionsOrder() {
        return menuSectionsOrder;
    }

    public void setMenuSectionsOrder(List<Long> menuSectionsOrder) {
        this.menuSectionsOrder = menuSectionsOrder;
    }
}