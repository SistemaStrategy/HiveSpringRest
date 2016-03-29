package com.hiverest.spring.data;

/**
 * Created by yann blanc on 3/9/16.
 */
public class Client {
    private int ID;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String toString() {
        return "Client number : " + ID + ", First name : " + firsName + ", Last name : " + lastName + ", Address : " + address;
    }

}
