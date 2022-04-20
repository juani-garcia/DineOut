package ar.edu.itba.paw.persistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@ComponentScan({ "ar.edu.itba.paw.persistence" })
@Configuration
public class TestConfig {

    @Value("classpath:sql/schema.sql")
    private Resource schemaSql;

    @Value("classpath:sql/hsqldb.sql")
    private Resource hsqldbSql;

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");  // TODO: add to env file
        ds.setPassword("");

        return ds;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());

        return dsi;
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScript(hsqldbSql);
        dbp.addScript(schemaSql);
        return dbp;
    }

}
