//package ar.edu.itba.paw.service;
//
//import ar.edu.itba.paw.model.Restaurant;
//import ar.edu.itba.paw.persistence.RestaurantDao;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class RestaurantServiceImplTest {
//
//    @InjectMocks
//    private RestaurantServiceImpl restaurantService;
//
//    @Mock
//    private RestaurantDao restaurantDao;
//    @Mock
//    private SecurityService securityService;
//    @Mock
//    private FavoriteService favoriteService;
//    @Mock
//    private ImageService imageService;
//
////    @Test
////    public void testCreateRestaurant() {
////        when(restaurantDao.create(any(), anyString(), any(), anyString(), anyString(), anyString(), any(), anyFloat(), anyFloat())).
////                thenReturn(TestValues.RESTAURANT);
////        when(securityService.getCurrentUser()).
////                thenReturn(Optional.of(TestValues.USER));
////
////        Restaurant restaurant = null;
////        System.out.println(TestValues.RESTAURANT);
////        System.out.println(TestValues.RESTAURANT);
////        System.out.println(TestValues.RESTAURANT);
////        try {
////            restaurant = restaurantService.create(TestValues.RESTAURANT_NAME, TestValues.IMAGE_BYTES, TestValues.RESTAURANT_ADDRESS, TestValues.RESTAURANT_MAIL, TestValues.RESTAURANT_DETAIL, TestValues.RESTAURANT_ZONE, TestValues.RESTAURANT_LAT, TestValues.RESTAURANT_LNG, TestValues.RESTAURANT_CATEGORIES, TestValues.RESTAURANT_SHIFTS);
////        } catch (Exception e) {
////            System.out.println(e.getClass());
////            Assert.fail("Unexpected error during operation create restaurant: " + e.getMessage());
////        }
////
////        Assert.assertNotNull(restaurant);
////    }
//
//    @Test
//    public void testCannotCreateIfAnonymous() {
//        when(securityService.getCurrentUser()).
//                thenReturn(Optional.empty());
//
//        Assert.assertThrows(UnauthenticatedUserException.class, () -> restaurantService.create(NAME, null, ADDRESS, MAIL, DETAIL, ZONE, CATEGORIES, SHIFTS_IDS));
//    }
////
////    @Test
////    public void testCannotCreateIfAlreadyOwner() {
////        when(securityService.getCurrentUser()).
////                thenReturn(Optional.of(USER));
////        when(restaurantDao.getByUserId(anyLong())).
////                thenReturn(Optional.of(new Restaurant(USER, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE, FAV_COUNT)));
////
////        Assert.assertThrows(IllegalStateException.class, () -> restaurantService.create(NAME, null, ADDRESS, MAIL, DETAIL, ZONE, CATEGORIES, SHIFTS_IDS));
////    }
////
////    @Test
////    public void testImageIsCreated() {
////        when(securityService.getCurrentUser()).
////                thenReturn(Optional.of(USER));
////        when(restaurantDao.getByUserId(anyLong())).
////                thenReturn(Optional.empty());
////        when(restaurantDao.create(any(), anyString(), any(), anyString(), anyString(), anyString(), any())).
////                thenReturn(new Restaurant(USER, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE, FAV_COUNT));
////        when(imageService.create(any())).
////                thenReturn(new Image(1, new byte[] {0, 1, 0, 1}));
////
////        Restaurant restaurant = restaurantService.create(NAME, new byte[] {0}, ADDRESS, MAIL, DETAIL, ZONE, CATEGORIES, SHIFTS_IDS);
////
////        Assert.assertNotNull(restaurant);
////        Assert.assertNotNull(restaurant.getImageId());
////        Assert.assertNotEquals(Long.valueOf(0), restaurant.getImageId());
////    }
//
//}
