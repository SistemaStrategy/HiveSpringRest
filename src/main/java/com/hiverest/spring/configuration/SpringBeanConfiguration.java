package com.hiverest.spring.configuration;

import com.hiverest.spring.dao.ClientDAO;
import com.hiverest.spring.dao.ClientDAOImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.IOException;
import java.sql.SQLException;
import javax.annotation.PostConstruct;

/**
 * Created by yann blanc on 3/2/16.
 */

@Configuration
@ComponentScan(basePackages = {"com.hiverest.spring"},
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SpringConfiguration.class) })
@PropertySource("file:${catalina.home}/conf/HiveSpringRest.properties")
public class SpringBeanConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    JdbcTemplateCreator jdbcTemplateCreator() {
        return new JdbcTemplateCreator();
    }

    @Bean
    DBCreator dbCreator() {
        return new DBCreator();
    }

    @Bean
    Log4JInitializer log4JInitializer() throws IOException  {
        return new Log4JInitializer();
    }

    @Bean
    JdbcTemplate jdbcTemplate(@Qualifier("jdbcTemplateCreator") JdbcTemplateCreator jdbcTemplateCreator) throws Exception {
        /*Create JDBC connection, also create DB if createDB = true*/
        return jdbcTemplateCreator().getTemplate();
    }

    @Bean
    ClientDAO clientDAO() {
        return new ClientDAOImpl();
    }

    @PostConstruct
    public void initLog4j() throws IOException {
        log4JInitializer().init();
    }

    /*CreateDB if app.db.createDB = true in configuration file*/
    @PostConstruct
    public void createDB() throws SQLException {
        dbCreator().createDB();
    }
}
