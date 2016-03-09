package com.hiverest.spring;

import com.hiverest.spring.dao.ClientDAO;
import com.hiverest.spring.dao.ClientDAOImpl;
import com.hiverest.spring.util.DatabaseCreator;
import org.apache.commons.logging.LogFactory;
import org.apache.hive.jdbc.HiveDriver;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created by yann blanc on 3/2/16.
*/
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hiverest.spring")
@PropertySource("file:${catalina.home}/conf/HiveSpringRest.properties")
public class SpringConfiguration {

    @Value("${catalina.home}")
    private String catalinaHome;

    @Value("${app.hive.url}")
    private String hiveUrl;

    @Value("${app.hive.port}")
    private String hivePort;

    @Value("${app.db.schema}")
    private String hiveSchema;

    @Value("${app.hive.auth}")
    private String hiveAuth;

    @Value("${app.hive.user}")
    private String hiveUser;

    @Value("${app.hive.password}")
    private String hivePassword;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    DataSource hiveDataSource() {
        String connUrl = hiveUrl + ":" + hivePort + "/" + hiveSchema;
        /*Add auth parameter if authentication mode is noSasl for HiveServer2*/
        if (hiveAuth.compareTo("noSasl") == 0) {
            connUrl = connUrl + ";auth=noSasl";
        }
        return new SimpleDriverDataSource(new HiveDriver(), connUrl, hiveUser, hivePassword);
    }

    @Bean
    JdbcTemplate jdbcTemplate(@Qualifier("hiveDataSource") DataSource hiveDataSource) throws Exception {
       return new JdbcTemplate(hiveDataSource);
    }

    @Bean
    ClientDAO clientDAO() {
        return new ClientDAOImpl();
    }

    @Bean
    DatabaseCreator databaseCreator() {
        return new DatabaseCreator();
    }

    @PostConstruct
    public void initLog4j() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(catalinaHome + "/conf/HiveSpringRest.properties"));
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(properties);
    }
}
