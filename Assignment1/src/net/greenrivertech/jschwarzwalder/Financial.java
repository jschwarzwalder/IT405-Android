package net.greenrivertech.jschwarzwalder;

import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public class Financial extends Employee implements Approve {
    //Each financial analyst has an expense approval authority for a certain amount. (1)

    public Financial( String name, char sex, Date birthday,Company employment_location) {
        super( name, sex, birthday, new Date(), "Financial Analyst", employment_location, false, true);
    }


    @Override
    public void approvePurchase() {
        System.out.println(getName() + " says buy whatever you want. Just keep the receipts.");
    }

    @Override
    public void approvePurchase(int value) {
        System.out.println(getName() + " has approved your purcahse of $" + value);
    }
}


