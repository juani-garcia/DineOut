package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Zone;

import java.time.LocalDateTime;

public class TestValues {

    private TestValues() {
        throw new AssertionError();
    }

    static final long ID = 1;
    static final String NAME = "Restaurant";
    static final long RESTAURANT_ID = 1;
    static final Long ORDERING = 1L;

    static final long USER_ID = 1;
    static final String USER_USERNAME = "user@mail.com";
    static final String USER_PASSWORD = "1234567890User";
    static final String USER_FIRST_NAME = "John";
    static final String USER_LAST_NAME = "Doe";
    static final User USER = new User(USER_ID, USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    static final User AUX_USER = new User(USER_ID + 1, "aux_" + USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    static final String RESTAURANT_NAME = "Restaurant";
    static final Long RESTAURANT_IMAGE_ID = null;
    static final String RESTAURANT_ADDRESS = "Address";
    static final String RESTAURANT_MAIL = "restaurant@mail.com";
    static final String RESTAURANT_DETAIL = null;
    static final Zone RESTAURANT_ZONE = Zone.ACASSUSO;
    static final Long RESTAURANT_FAV_COUNT = 0L;
    static final Restaurant RESTAURANT = new Restaurant(RESTAURANT_ID, USER, RESTAURANT_NAME, RESTAURANT_IMAGE_ID, RESTAURANT_ADDRESS, RESTAURANT_MAIL,
            RESTAURANT_DETAIL, RESTAURANT_ZONE);

    static final int AMOUNT = 1;
    static final LocalDateTime DATETIME = LocalDateTime.now();
    static final String COMMENTS = "";
    static final boolean IS_CONFIRMED = false;

    static final long RESTAURANT_USER_ID = 1;
}
