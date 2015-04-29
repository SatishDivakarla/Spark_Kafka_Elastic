package com.elasticsearch.practice.model;

/**
 * Created by SatishDivakarla on 4/18/15.
 */
public class EmployeeName {
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String firstName;
    private String lastName;


}
