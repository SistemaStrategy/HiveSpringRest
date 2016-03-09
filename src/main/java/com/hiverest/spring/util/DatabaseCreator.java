package com.hiverest.spring.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by yann blanc on 3/9/16.
 */
public class DatabaseCreator {

    private static final Log log = LogFactory.getLog(DatabaseCreator.class);

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Value("${app.db.createDB}")
    private String createDB;

    @Value("${app.db.schema}")
    private String schema;

    @Value("${app.db.datafile.client.location}")
    private String datafileClientLocation;

    @Value("${app.db.datafile.client.path}")
    private String datafileClientPath;

    @PostConstruct
    public void createDatabases() {
        if(createDB.compareTo("true") == 0) {
            this.createClientDB();
        }
    }

    public void createClientDB() {
        log.info("Creating databases ... ");
        log.info("createClientDB : schema = " + schema +
                " datafileClientLocation = " + datafileClientLocation +
                " datafileClientPath = " + datafileClientPath);

        String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS " + schema;

        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + schema + ".client " +
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

        String sqlInsertData = "LOAD DATA " + location + " INPATH '" + datafileClientPath + "' OVERWRITE INTO TABLE client";

        log.debug("Executing : " + sqlCreateDB);
        jdbcTemplateObject.execute(sqlCreateDB);
        log.debug("Executing : " + sqlCreateTable);
        jdbcTemplateObject.execute(sqlCreateTable);
        log.debug("Executing : " + sqlInsertData);
        jdbcTemplateObject.execute(sqlInsertData);
    }
}
