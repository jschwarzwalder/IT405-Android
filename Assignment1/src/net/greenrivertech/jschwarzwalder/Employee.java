package net.greenrivertech.jschwarzwalder;

import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public abstract class Employee {
    private int empID;
    private String name;
    private Character sex;
    private Date birthday;
    private Date joining_date;
    private String employment_type;
    private Company employment_location;
    private Boolean can_sign_checks;
    private Boolean can_approve_expense;


    public Employee(int empID, String name, Character sex, Date birthday, Date joining_date,
                    String employment_type, Company employment_location, Boolean can_sign_checks, Boolean can_approve_expense) {

        this.empID = empID;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.joining_date = joining_date;
        this.employment_type = employment_type;
        this.employment_location = employment_location;
        this.can_sign_checks = can_sign_checks;
        this.can_approve_expense = can_approve_expense;

    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(Date joining_date) {
        this.joining_date = joining_date;
    }
}
