package com.hiverest.spring;

import com.hiverest.spring.util.DatabaseCreator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by yann blanc on 3/9/16.
 */
@Component
public class ContextListener implements ApplicationListener<ContextStartedEvent> {

    private static final Log log = LogFactory.getLog(DatabaseCreator.class);

    @Autowired
    private DatabaseCreator databaseCreator;

    @Value("${app.hive.createDB}")
    private String createDB;


    public void onApplicationEvent(ContextStartedEvent event) {
        log.info("ContextStartedEvent ... ");
        if(createDB.compareTo("true") == 0) {
            log.info("Creating databases ... ");
            databaseCreator.createClientDB();
        }
    }
}
