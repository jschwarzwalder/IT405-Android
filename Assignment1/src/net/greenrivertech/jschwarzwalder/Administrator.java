package net.greenrivertech.jschwarzwalder;

import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public class Administrator extends Employee {
    public Administrator(int empID, String name, Character sex, Date birthday, Date joining_date,
                         Company employment_location) {
        super(empID, name, sex, birthday, joining_date, "Administrator", employment_location, true, false);
        // Each administrator has a check signing authority of a specified amount for approved expenses.

    }

}
