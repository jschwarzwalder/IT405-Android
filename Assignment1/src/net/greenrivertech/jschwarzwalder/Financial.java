package net.greenrivertech.jschwarzwalder;

import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public class Financial extends Employee {
    //Each financial analyst has an expense approval authority for a certain amount. (1)

    public Financial(int empID, String name, Character sex, Date birthday,
                     Date joining_date, Company employment_location) {
        super(empID, name, sex, birthday, joining_date, "Financial Analyst", employment_location, false, true);
    }
}


