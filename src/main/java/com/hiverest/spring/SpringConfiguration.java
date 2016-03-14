package com.hiverest.spring;

import com.hiverest.spring.dao.ClientDAO;
import com.hiverest.spring.dao.ClientDAOImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hive.jdbc.HiveDriver;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
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

    @Value("${app.db.createDB}")
    private String createDB;

    @Value("${app.db.datafile.client.location}")
    private String datafileClientLocation;

    @Value("${app.db.datafile.client.path}")
    private String datafileClientPath;

    private static final Log log = LogFactory.getLog(SpringConfiguration.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private DataSource hiveDataSource(boolean withSchema) {
        String connUrl;
        if(withSchema) {
            connUrl = hiveUrl + ":" + hivePort + "/" + hiveSchema;
        } else {
            connUrl = hiveUrl + ":" + hivePort + "/";
        }
        /*Add auth parameter if authentication mode is noSasl for HiveServer2*/
        if (hiveAuth.compareTo("noSasl") == 0) {
            connUrl = connUrl + ";auth=noSasl";
        }
        return new SimpleDriverDataSource(new HiveDriver(), connUrl, hiveUser, hivePassword);
    }

    private void createClientDB(JdbcTemplate jdbcTemplate) {
        log.info("Creating databases ... ");

        String connUrl = hiveUrl + ":" + hivePort + "/" + hiveSchema;
        /*Add auth parameter if authentication mode is noSasl for HiveServer2*/
        if (hiveAuth.compareTo("noSasl") == 0) {
            connUrl = connUrl + ";auth=noSasl";
        }

        log.info("createClientDB : schema = " + hiveSchema +
                " datafileClientLocation = " + datafileClientLocation +
                " datafileClientPath = " + datafileClientPath);

        String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS " + hiveSchema;

        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + hiveSchema + ".client " +
                "(id INT, " +
                " firstname STRING, " +
                " lastname STRING, " +
                " address STRING)" +
                " ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\073'" +
                " STORED AS TEXTFILE";

        /*If no location specified, it's HDFS, otherwise specify LOCAL if the file is local*/
        String location = "";
        if (datafileClientLocation.compareTo("local") == 0) {
            location = "LOCAL";
        }

        String sqlInsertData = "LOAD DATA " + location + " INPATH '" + datafileClientPath + "' OVERWRITE INTO TABLE " + hiveSchema + ".client";

        log.debug("Executing : " + sqlCreateDB);
        jdbcTemplate.execute(sqlCreateDB);
        log.debug("Executing : " + sqlCreateTable);
        jdbcTemplate.execute(sqlCreateTable);
        log.debug("Executing : " + sqlInsertData);
        jdbcTemplate.execute(sqlInsertData);
    }

    @Bean
    JdbcTemplate jdbcTemplate() throws Exception {
        if(createDB.compareTo("true") == 0) {
            JdbcTemplate tmpTemplate = new JdbcTemplate(hiveDataSource(false));
            createClientDB(tmpTemplate);
            tmpTemplate.getDataSource().getConnection().close();
        }
       return new JdbcTemplate(hiveDataSource(true));
    }

    @Bean
    ClientDAO clientDAO() {
        return new ClientDAOImpl();
    }

    @PostConstruct
    public void initLog4j() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(catalinaHome + "/conf/HiveSpringRest.properties"));
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(properties);
    }
}
