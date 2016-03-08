package com.hiverest.spring;

/**
 * Created by root on 3/2/16.
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private HiveClient hiveTemplate;

    private static final Log log = LogFactory.getLog(Controller.class);

    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", defaultValue="Yann") String name) {
        return "Hello " + name;
    }

    @RequestMapping("/tables")
    public List<String> getTables() {
        List<String> tables;
        log.info("/tables");
        tables = hiveTemplate.execute("show tables");
        log.info("/tables completed");
        return tables;
    }

}