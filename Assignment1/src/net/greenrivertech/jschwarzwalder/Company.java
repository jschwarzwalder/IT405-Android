package net.greenrivertech.jschwarzwalder;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Jami on 6/30/2016.
 */
public class Company {
    private String name;
    private String address;
    private int company_identifer;
    private HashSet<Employee> Employees;

    public Company (){
        String name;
        String address;
        int company_identifer;
        HashSet<Employee> Employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCompany_identifer() {
        return company_identifer;
    }

    public void setCompany_identifer(int company_identifer) {
        this.company_identifer = company_identifer;
    }

    public HashSet<Employee> getEmployees() {
        return Employees;
    }

    public void addEmployees(Employee employee) {
        Employees.add(employee);
    }


}
