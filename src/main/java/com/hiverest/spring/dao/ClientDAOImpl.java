package com.hiverest.spring.dao;

import com.hiverest.spring.data.Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by yann blanc on 3/9/16.
 */
public class ClientDAOImpl implements ClientDAO {

    private static final Log log = LogFactory.getLog(ClientDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    public void createClient(int numClient, String firstName, String lastName, String address) {
        String SQL = "insert into Client (id, firstname, lastname, address) values (?, ?, ?, ?)";
        jdbcTemplateObject.update( SQL, numClient, firstName, lastName, address);
        log.info("Created Record numClient = " + numClient + " firstName = " + firstName + " lastName = "
                + lastName + " address = " + address);
        return;
    }

    public Client getClientById(int numClient) {
        String SQL = "select * from client where id = ?";
        Client client = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{numClient}, new ClientMapper());
        return client;

    }

    public List<Client> listClients() {
        String SQL = "select * from client";
        List <Client> clients = jdbcTemplateObject.query(SQL,
                new ClientMapper());
        return clients;
    }
}
