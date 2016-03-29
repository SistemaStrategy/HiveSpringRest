package com.hiverest.spring.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hive.jdbc.HiveDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by yann blanc on 3/15/16.
 */
@Component
public class DBCreator {
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


    private static final Log log = LogFactory.getLog(DBCreator.class);

    private void createClientDB() throws SQLException {

        String connUrl = hiveUrl + ":" + hivePort + "/";

        /*Add auth parameter if authentication mode is noSasl for HiveServer2*/
        if (hiveAuth.compareTo("noSasl") == 0) {
            connUrl = connUrl + ";auth=noSasl";
        }

        DataSource hiveDataSource = new SimpleDriverDataSource(
                new HiveDriver(),
                connUrl,
                hiveUser,
                hivePassword);

        JdbcTemplate tmpTemplate = new JdbcTemplate(hiveDataSource);

        log.info("Creating databases ... ");

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
        tmpTemplate.execute(sqlCreateDB);
        log.debug("Executing : " + sqlCreateTable);
        tmpTemplate.execute(sqlCreateTable);
        log.debug("Executing : " + sqlInsertData);
        tmpTemplate.execute(sqlInsertData);

        tmpTemplate.getDataSource().getConnection().close();
    }

    void createDB() throws SQLException {
        if(createDB.compareTo("true") == 0 ) {
            createClientDB();
        }
    }

}
