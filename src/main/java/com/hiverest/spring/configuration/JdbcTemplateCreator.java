package com.hiverest.spring.configuration;

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
public class JdbcTemplateCreator {

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

    JdbcTemplate getTemplate() throws SQLException {

        String connUrl = hiveUrl + ":" + hivePort + "/" + hiveSchema;

        /*Add auth parameter if authentication mode is noSasl for HiveServer2*/
        if (hiveAuth.compareTo("noSasl") == 0) {
            connUrl = connUrl + ";auth=noSasl";
        }

        /*Create DataSource and JdbcTemplate*/
        DataSource hiveDataSource = new SimpleDriverDataSource(new HiveDriver(), connUrl, hiveUser, hivePassword);

        return new JdbcTemplate(hiveDataSource);
    }

}
