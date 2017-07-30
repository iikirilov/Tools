/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ivaylo Kirilov iikirilov@gmail.com
 */
public class CDate {
    
    private static final Calendar c = Calendar.getInstance();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Date d = new Date();
        System.out.println(dateDiff(getDate("01.01.1970"),d,TimeUnit.DAYS));
        System.out.println(getDay(d));        
        System.out.println(getWeek(d, true));
        System.out.println(getWeek(d, false));
        System.out.println(getMonth(d));
        System.out.println(getYear(d));        
        System.out.println(d);
        System.out.println(getString(d));
        System.out.println(getDate(getString(d)));
    }
    
    public static Date getDate(String s){
        Date d = null;
        try {
            d = sdf.parse(s);
        } catch (ParseException ex) {
            System.err.println(s + " is in an invalid format.");
        }
        return d;
    } 
    
    public static String getString(Date d){
        String s = sdf.format(d);
        return s;        
    }
    
    public static int getYear(Date date){
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }
    
    public static int getWeek(Date date, boolean ofYear){
        c.setTime(date);
        if(ofYear){
            return c.get(Calendar.WEEK_OF_YEAR);
        }
        else{
            return c.get(Calendar.WEEK_OF_MONTH);
        }
    }    
    
    public static String getMonth(Date date){
        c.setTime(date);
        String month = "invalid month";
        switch (c.get(Calendar.MONTH)) {
            case 0 : month = "January";
                break;
            case 1 : month = "Febuary";
                break;
            case 2 : month = "March";
                break;
            case 3 : month = "April";
                break;
            case 4 : month = "May";
                break;
            case 5 : month = "June";
                break;
            case 6 : month = "July";
                break;
            case 7 : month = "August";
                break;
            case 8 : month = "September";
                break;
            case 9 : month = "October";
                break;
            case 10 : month = "November";
                break;
            case 11 : month = "December";
                break;
        }
        return month;
    }
    public static String getDay(Date date){
        c.setTime(date);
        String day = "invalid day";
        switch (c.get(Calendar.DAY_OF_WEEK)) {            
            case 1 : day = "Sunday";
                break;
            case 2 : day = "Monday";
                break;
            case 3 : day = "Tuesday";
                break;
            case 4 : day = "Wednesday";
                break;
            case 5 : day = "Thursday";
                break;
            case 6 : day = "Friday";
                break;    
            case 7 : day = "Saturday";
                break;
        }
        return day;        
    }
    
    public static Date addDay(Date d, int days){
        c.setTime(d);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }
    
    public static Date addWeek(Date d, int weeks){
        c.setTime(d);
        c.add(Calendar.WEEK_OF_YEAR, weeks);
        return c.getTime();
    }
    
    public static Date addMonth(Date d, int months){
        c.setTime(d);
        c.add(Calendar.MONTH, months);
        return c.getTime();
    }
    
    public static Date addYear(Date d, int year){
        c.setTime(d);
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }
    
    public static long dateDiff(Date a, Date b, TimeUnit timeUnit){
        long diffMilli = b.getTime() - a.getTime();
        return timeUnit.convert(diffMilli,TimeUnit.MILLISECONDS);
    }
    
}
