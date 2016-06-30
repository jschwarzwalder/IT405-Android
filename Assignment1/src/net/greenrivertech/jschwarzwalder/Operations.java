package net.greenrivertech.jschwarzwalder;

import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public class Operations extends Employee {

    public Operations(int empID, String name, Character sex, Date birthday, Date joining_date,
                      Company employment_location) {
        super(empID, name, sex, birthday, joining_date, "Operations", employment_location, false, false);
    }
}
