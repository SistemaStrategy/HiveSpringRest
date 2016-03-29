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

    @RequestMapping("/client/all")
    public List<Client> getAllClient() {
        log.info("getAllClient");
        List<Client> clients = clientDAO.listClients();
        log.info("getAllClient completed");
        return clients;
    }

    @RequestMapping("/client/{id}")
    public Client getClientById(@PathVariable("id") int id) {
        log.info("getClientById");
        Client client = clientDAO.findById(id);
        log.info("getClientById completed");
        return client;
    }


}