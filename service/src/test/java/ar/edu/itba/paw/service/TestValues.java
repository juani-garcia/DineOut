package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestValues {

    TestValues() {
        throw new AssertionError();
    }

    static final long RESTAURANT_ID = 1;
    static final long MENU_ITEM_ID = 1;
    static final long RESERVATION_ID = 1;
    static final long MENU_SECTION_ID = 1;
    static final long USER_ID = 1;
    static final String RESTAURANT_NAME = "Restaurant";
    static final Long MENU_SECTION_ORDERING = 1L;
    static final Long MENU_ITEM_ORDERING = 1L;

    static final String USER_USERNAME = "user@mail.com";
    static final String USER_PASSWORD = "1234567890User";
    static final String USER_FIRST_NAME = "John";
    static final String USER_LAST_NAME = "Doe";
    static final User USER = new User(USER_ID, USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    static final User AUX_USER = new User(USER_ID + 1, "aux_" + USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    static final Long RESTAURANT_IMAGE_ID = null;
    static final String RESTAURANT_ADDRESS = "Address";
    static final String RESTAURANT_MAIL = "restaurant@mail.com";
    static final String RESTAURANT_DETAIL = null;
    static final Zone RESTAURANT_ZONE = Zone.ACASSUSO;
    static final Long RESTAURANT_FAV_COUNT = 0L;
    static final Float RESTAURANT_LAT = 0f, RESTAURANT_LNG = 0f;
    static final List<Long> RESTAURANT_CATEGORIES = new ArrayList<>();
    static final List<Long> RESTAURANT_SHIFTS = new ArrayList<>();
    static final Restaurant RESTAURANT = new Restaurant(RESTAURANT_ID, USER, RESTAURANT_NAME, RESTAURANT_ADDRESS, RESTAURANT_MAIL,
            RESTAURANT_DETAIL, RESTAURANT_ZONE, RESTAURANT_LAT, RESTAURANT_LNG);

    static final int RESERVATION_AMOUNT = 1;
    static final LocalDateTime RESERVATION_DATETIME = LocalDateTime.now();
    static final String RESERVATION_COMMENTS = "";
    static final boolean RESERVATION_IS_CONFIRMED = false;
    static final Reservation RESERVATION = new Reservation(RESERVATION_ID, RESTAURANT, USER, RESERVATION_AMOUNT, RESERVATION_DATETIME, RESERVATION_COMMENTS, RESERVATION_IS_CONFIRMED);

    static final String DETAIL = "Detail";
    static final double PRICE = 5.0;
    static final byte[] IMAGE_BYTES = new byte[] {0, 1};
    static final long IMAGE_ID = 1;


    static final String MENU_SECTION_NAME = "Section";
    static final long SECTION_RESTAURANT_ID = RESTAURANT_ID;
    static final MenuSection MENU_SECTION = new MenuSection(MENU_SECTION_ID,
            MENU_SECTION_NAME, RESTAURANT, MENU_SECTION_ORDERING);
    static final String MENU_ITEM_NAME = "Menu item";
    static final Image MENU_ITEM_IMAGE = null;
    static final MenuItem MENU_ITEM = new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION, MENU_ITEM_ORDERING, MENU_ITEM_IMAGE);


    static final String RESTAURANT_REVIEW_NAME = "veri gut";
    static final String RESTAURANT_REVIEW_NAME_2 = "veri BAT";
    static final long RESTAURANT_REVIEW_RATING = 3;
    static final long RESTAURANT_REVIEW_RATING_2 = 1;
    static final long RESTAURANT_REVIEW_ID = 1;
    static final RestaurantReview RESTAURANT_REVIEW = new RestaurantReview(RESTAURANT_REVIEW_ID, RESTAURANT_REVIEW_NAME, RESTAURANT_REVIEW_RATING, RESTAURANT, USER);


}
