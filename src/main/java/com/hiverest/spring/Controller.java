package com.hiverest.spring;

/**
 * Created by yann blanc on 3/2/16.
 */
import com.hiverest.spring.dao.ClientDAO;
import com.hiverest.spring.data.Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private ClientDAO clientDAO;

    private static final Log log = LogFactory.getLog(Controller.class);

    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", defaultValue="Yann") String name) {
        return "Hello " + name;
    }

    /*Change return type, make it generic*/
    @RequestMapping("/getAll/{tableName}")
    public List<String> getAll(@PathVariable String tableName) {

        log.info("/tables");

        List<String> result = new ArrayList<String>();

        if (tableName.compareTo("client") == 0) {
            List<Client> clients = clientDAO.listClients();
            for(Client c : clients ) {
                result.add(c.toString());
            }
        } else {
            result.add("Table " + tableName + " not existing ... ");
        }

        log.info("/tables completed");
        return result;
    }

}