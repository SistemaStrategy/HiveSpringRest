package com.hiverest.spring.dao;

import com.hiverest.spring.data.Client;

import java.util.List;

/**
 * Created by yann blanc on 3/9/16.
 */
public interface ClientDAO {

    /**
     * Method to create a record in the client table
     * @param ID client ID, unique
     * @param firstName first name
     * @param lastName last name
     * @param address address
     */
    public void insert(int ID, String firstName, String lastName, String address);

    /**
     * Methode to retrieve a client by client number
     * @param ID client ID, unique
     * @return The client having the client ID specified or NULL if not existing
     */
    public Client findById(int ID);

    /**
     * Method to retrieve all clients
     * @return A list containing all clients
     */
    public List<Client> listClients();
}
