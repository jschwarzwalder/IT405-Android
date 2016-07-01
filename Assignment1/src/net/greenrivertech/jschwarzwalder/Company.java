package net.greenrivertech.jschwarzwalder;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Jami on 6/30/2016.
 */
public class Company {
    private String name;
    private String address;
    private int companyIdentifier;
    private LinkedList<Employee> employees = new LinkedList<Employee>();

    public Company(String name, String address, int companyIdentifier) {
        this.name = name;
        this.address = address;
        this.companyIdentifier = companyIdentifier;
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

    public int getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifer(int companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public void listEmployees() {
        Iterator<Employee> employeeList = employees.listIterator();
        while (employeeList.hasNext()) {
            Employee employee = employeeList.next();
            System.out.print(employee.getName());

            System.out.print(" - ");
            System.out.print(employee.getEmployment_type());

            System.out.print(" (");
            System.out.print(employee.getEmpID());

            System.out.print(" , ");
            System.out.print(employee.getSex());

            System.out.print(", Joined: ");
            System.out.print(employee.getJoiningDate());

            System.out.print(", Birthdate: ");
            System.out.print(employee.getBirthday());

            if (employee.getCan_approve_expense()){
                System.out.print(", Can approve expenses");
            } else {
                System.out.print(", CANNOT approve expenses");
            }

            if (employee.getCan_sign_checks()){
                System.out.print(", Can sign checks");
            } else {
                System.out.print(", CANNOT sign checks");
            }

            System.out.println(")");
        }

    }



    public void addEmployee(Employee employee) {
       // System.out.println(employee);
        employees.add(employee);
    }


}
