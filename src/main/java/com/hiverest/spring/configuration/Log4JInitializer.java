package com.hiverest.spring.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yann blanc on 3/15/16.
 */
@Component
public class Log4JInitializer {

    @Value("${catalina.home}")
    private String catalinaHome;

    void init() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(catalinaHome + "/conf/HiveSpringRest.properties"));
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(properties);
    }
}
