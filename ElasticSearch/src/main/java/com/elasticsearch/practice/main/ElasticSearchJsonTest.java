package com.elasticsearch.practice.main;

import com.elasticsearch.practice.model.Employee;
import com.elasticsearch.practice.model.EmployeeName;
import com.elasticsearch.practice.util.ElasticSearchUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.Client;

import java.io.IOException;

/**
 * Created by SatishDivakarla on 4/18/15.
 */
public class ElasticSearchJsonTest {
    public static void main(String args[]){

        ElasticSearchUtil helper = new ElasticSearchUtil();
        Client client = helper.getClient();

        EmployeeName employeeName = new EmployeeName();
        employeeName.setFirstName("Kishore");
        employeeName.setLastName("Murakonda");
        Employee employee = new Employee();
        employee.setName(employeeName);
        employee.setEmployeeId("101");
        employee.setDesignation("Developer");
        ObjectMapper mapper = new ObjectMapper();

        try {
            helper.indexRecord(mapper.writeValueAsString(employee));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
