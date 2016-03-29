package com.hiverest.spring.dao;

import com.hiverest.spring.data.Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yann blanc on 3/9/16.
 */
@Component
public class ClientDAOImpl implements ClientDAO {

    private static final Log log = LogFactory.getLog(ClientDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    public void insert(int ID, String firstName, String lastName, String address) {
        String SQL = "insert into table client values (?, ?, ?, ?)";
        jdbcTemplateObject.update( SQL, ID, firstName, lastName, address);
        log.info("Created Record numClient = " + ID + " firstName = " + firstName + " lastName = "
                + lastName + " address = " + address);
        return;
    }

    public Client findById(int ID) {
        String SQL = "select * from client where id = ?";
        Client client = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{ID}, new ClientMapper());
        return client;

    }

    public List<Client> listClients() {
        String SQL = "select * from client";
        List <Client> clients = jdbcTemplateObject.query(SQL,
                new ClientMapper());
        return clients;
    }
}
