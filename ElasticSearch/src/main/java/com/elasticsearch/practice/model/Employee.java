package com.elasticsearch.practice.model;

/**
 * Created by SatishDivakarla on 4/16/15.
 */
public class Employee {

    private EmployeeName name;
    private String designation;
    private String employeeId;

    public EmployeeName getName() {
        return name;
    }

    public void setName(EmployeeName name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
