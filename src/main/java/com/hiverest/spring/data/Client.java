package com.hiverest.spring.data;

/**
 * Created by yann blanc on 3/9/16.
 */
public class Client {
    private int numClient;
    private String lastName;
    private String firsName;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumClient() {
        return numClient;
    }

    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    public String toString() {
        return "Client number : " + numClient + ",First name : " + firsName + ",Last name : " + lastName + ",Address : " + address;
    }

}
