package net.greenrivertech.jschwarzwalder;

import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public class Administrator extends Employee implements Sign {
    public Administrator(String name, char sex, Date birthday,Company employment_location) {
        super( name, sex, birthday, new Date(), "Administrator", employment_location, true, false);
        // Each administrator has a check signing authority of a specified amount for approved expenses.

    }

    @Override
    public void signCheck(){
        System.out.println("Blank Check Signed by " + getName());

    }

    @Override
    public void signCheck(int value){
        System.out.println("Check for $" + value +" Signed by "+ getName());

    }
}
