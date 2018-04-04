package com.example.kejin.iot_demo.data_class;

import java.util.List;

/**
 * Created by kejin on 04/04/2018.
 */

public class User {
    String firstName;
    String LastName;
    String email;
    List<DataRecord>sharing;
    List<DataRecord>renting;

    public User() {

    }

    public User(String firstName, String lastName, String email, List<DataRecord> sharing, List<DataRecord> renting) {
        this.firstName = firstName;
        LastName = lastName;
        this.email = email;
        this.sharing = sharing;
        this.renting = renting;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DataRecord> getSharing() {
        return sharing;
    }

    public void setSharing(List<DataRecord> sharing) {
        this.sharing = sharing;
    }

    public List<DataRecord> getRenting() {
        return renting;
    }

    public void setRenting(List<DataRecord> renting) {
        this.renting = renting;
    }
}
