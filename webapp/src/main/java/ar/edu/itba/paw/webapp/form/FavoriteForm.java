package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;

public class FavoriteForm {

    @NotNull
    private boolean upVote = false;

    public boolean isUpVote() {
        return upVote;
    }

    public void setUpVote(boolean upVote) {
        this.upVote = upVote;
    }

}
