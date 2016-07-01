package net.greenrivertech.jschwarzwalder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jami on 6/30/2016.
 */
public class ControllerAssignement1 {

    public static void main(String[] args){

        Company CompanyA= new Company("Cat's Pajamas", "Redmond, WA", 527 );
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date date = null;
        try {
            date = formatter.parse("10/20/83");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Employee seniorDev = new Administrator("Paul", 'M', date, CompanyA) ;
        CompanyA.addEmployee(seniorDev);

        Calendar birthdate = new GregorianCalendar(1989, Calendar.JULY, 18);
        Employee Designer = new Administrator("Phoenix", 'M', birthdate.getTime(), CompanyA) ;
        CompanyA.addEmployee(Designer);

        Calendar birthdate2 = new GregorianCalendar(1975, Calendar.APRIL, 12);
        Employee Bookkeeper = new Financial("Laura", 'F', birthdate2.getTime(), CompanyA) ;
        CompanyA.addEmployee(Bookkeeper);

        Calendar birthdate3 = new GregorianCalendar(1990, Calendar.MARCH, 3);
        Employee Artist = new Operations("Lyra", 'F', birthdate3.getTime(), CompanyA) ;
        CompanyA.addEmployee(Artist);

        Calendar birthdate4 = new GregorianCalendar(1983, Calendar.AUGUST, 13);
        Employee UX = new Operations("Sally", 'F', birthdate4.getTime(), CompanyA) ;
        CompanyA.addEmployee(UX);

        printCompany(CompanyA);
        System.out.println();

        Company CompanyB= new Company("Pierce County Library", "Puyallup, WA", 15420 );

        Calendar birthdate5 = new GregorianCalendar(1970, Calendar.JANUARY, 19);
        Employee Director = new Administrator("Georgia", 'F', birthdate5.getTime(), CompanyB) ;
        CompanyB.addEmployee(Director);

        Calendar birthdate6 = new GregorianCalendar(1955, Calendar.NOVEMBER, 2);
        Employee DeputyDirector = new Administrator("Sally", 'F', birthdate6.getTime(), CompanyB) ;
        CompanyB.addEmployee(DeputyDirector);

        Calendar birthdate7 = new GregorianCalendar(1973, Calendar.SEPTEMBER, 25);
        Employee Payroll = new Financial("Christy", 'F', birthdate7.getTime(), CompanyB) ;
        CompanyB.addEmployee(Payroll);

        Calendar birthdate8 = new GregorianCalendar(1965, Calendar.FEBRUARY, 19);
        Employee Finances = new Financial("Dave", 'M', birthdate8.getTime(), CompanyB) ;
        CompanyB.addEmployee(Finances);

        Calendar birthdate9 = new GregorianCalendar(1978, Calendar.DECEMBER, 30);
        Employee Librarian = new Operations("Meredith", 'F', birthdate9.getTime(), CompanyB) ;
        CompanyB.addEmployee(Librarian);

        printCompany(CompanyB);
        System.out.println();
    }


    public static void printCompany(Company company){
        System.out.println(company.getName());
        System.out.println(company.getAddress());
        System.out.println();
        System.out.println("Employees");
        System.out.println("==========");
        company.listEmployees();
    }
}
