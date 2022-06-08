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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({"ar.edu.itba.paw.persistence"})
@Configuration
public class TestConfig {

    @Value("classpath:sql/schema.sql")
    private Resource schemaSql;

    @Value("classpath:sql/hsqldb.sql")
    private Resource hsqldbSql;
    @Value("classpath:sql/hsqldb1.sql")
    private Resource hsqldbSql1;
    @Value("classpath:sql/hsqldb3.sql")
    private Resource hsqldbSql3;
    @Value("classpath:sql/hsqldb5.sql")
    private Resource hsqldbSql5;
    @Value("classpath:sql/hsqldb6.sql")
    private Resource hsqldbSql6;
    @Value("classpath:sql/hsqldb7.sql")
    private Resource hsqldbSql7;
    @Value("classpath:sql/hsqldb8.sql")
    private Resource hsqldbSql8;
    @Value("classpath:sql/hsqldb9.sql")
    private Resource hsqldbSql9;

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
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
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.model");
        factoryBean.setDataSource(dataSource());

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update"); // Research and consider using "validate"!
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");

        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.show-sql", "true");
        properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");
        properties.setProperty("format_sql", "true");

        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScripts(hsqldbSql,
                hsqldbSql1, hsqldbSql3,
                // hsqldbSql5, hsqldbSql6,
                hsqldbSql7, hsqldbSql8);
        // dbp.addScript(schemaSql);
        return dbp;
    }

}
