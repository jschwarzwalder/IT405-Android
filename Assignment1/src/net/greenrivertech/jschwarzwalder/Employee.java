package net.greenrivertech.jschwarzwalder;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public abstract class Employee {
    private int empID;
    private String name;
    private char sex;
    private Date birthday;
    private Date joiningDate;
    private String employment_type;
    private Company employment_location;
    private Boolean can_sign_checks;
    private Boolean can_approve_expense;
    private static int nextID = 10001;


    public Employee( String name, char sex, Date birthday, Date joiningDate,
                    String employment_type, Company employment_location, Boolean can_sign_checks, Boolean can_approve_expense) {

        this.empID = nextID;
        nextID += 5;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.joiningDate = joiningDate;
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

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        Format formatter = new SimpleDateFormat("MM-dd-yyyy ");
        return formatter.format(birthday);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getJoiningDate() {
        Format formatter = new SimpleDateFormat("MM-dd-yyyy ");
        return formatter.format(joiningDate);
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public Boolean getCan_sign_checks() {
        return can_sign_checks;
    }

    public Boolean getCan_approve_expense() {
        return can_approve_expense;
    }

    public Company getEmployment_location() {
        return employment_location;
    }
}
