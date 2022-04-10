package ar.edu.itba.paw.persistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class ReservationJdbcDaoTest {

    private ReservationJdbcDao reservationDao;

    @Before
    public void setUp() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("");

        reservationDao = new ReservationJdbcDao(ds);
    }

    @Test
    public void testCreateReservation() {
        // precondiciones

        // ejercitacion


        // postcondiciones
        return;
    }

}
