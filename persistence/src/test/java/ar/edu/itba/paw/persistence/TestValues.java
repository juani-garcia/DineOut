package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Zone;

public class TestValues {

    private TestValues() {
        throw new AssertionError();
    }

    /* RESTAURANT VALUES */
    static final String RESTAURANT_TABLE = "restaurant";
    static final long RESTAURANT_ID = 1;
    static final String RESTAURANT_NAME = "Atuel";
    static final Long RESTAURANT_IMAGE_ID = null;
    static final String RESTAURANT_ADDRESS = "Los Patos 2301";
    static final String RESTAURANT_MAIL = "atuel@mail.com";
    static final String RESTAURANT_DETAIL = "Detalle de Atuel";
    static final Zone RESTAURANT_ZONE = Zone.ADROGUE;
    static final Long RESTAURANT_FAV_COUNT = 0L;

    /* USER VALUES */
    static final long USER_ID = 1;
    static final String USER_USERNAME = "user@mail.com";
    static final String USER_FIRST_NAME = "John";
    static final String USER_LAST_NAME = "Doe";
    static final String USER_PASSWORD = "1234567890User";
    static final String USER_NEW_PASSWORD = "User1234567890";
    static final String USER_TABLE = "account";
}
