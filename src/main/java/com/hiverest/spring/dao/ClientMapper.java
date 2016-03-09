package com.hiverest.spring.dao;

import com.hiverest.spring.data.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yann blanc on 3/9/16.
 */
public class ClientMapper implements RowMapper<Client> {
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {
        Client client = new Client();
        client.setNumClient(resultSet.getInt("id"));
        client.setFirsName(resultSet.getString("firstname"));
        client.setLastName(resultSet.getString("lastname"));
        client.setAddress(resultSet.getString("address"));
        return client;
    }
}
