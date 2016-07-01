package net.greenrivertech.jschwarzwalder;

import java.util.Date;

/**
 * Created by Jami on 6/30/2016.
 */
public class Operations extends Employee {

    public Operations( String name, char sex, Date birthday, Company employmentLocation) {
        super( name, sex, birthday, new Date(), "Operations", employmentLocation, false, false);
    }
}
