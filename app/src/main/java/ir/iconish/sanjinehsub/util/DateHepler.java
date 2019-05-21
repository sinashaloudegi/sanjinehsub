package ir.iconish.sanjinehsub.util;


import java.util.Calendar;
import java.util.TimeZone;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class DateHepler {
    public static String convertTimeStampToPersianDate(long timestamp) {
        PersianDateFormat pdformater = new PersianDateFormat("Y/m/d");

        PersianDate persianDate= new PersianDate( timestamp);
        return pdformater.format(persianDate);
    }

    public static String getPersianDate(long date){

        Calendar serverDate=   convertTimeWithTimeZome(date);

        int serverYear=serverDate.get(Calendar.YEAR);
        int serverMonth=serverDate.get(Calendar.MONTH)+1;
        int serverDay=serverDate.get(Calendar.DAY_OF_MONTH);






            String persianDate = convertGregorianToPersiabDate(serverYear,serverMonth,serverDay);


        return persianDate;
    }
    public static String convertGregorianToPersiabDate(int year,int month,int day){

        saman.zamani.persiandate.PersianDate persianDate=new saman.zamani.persiandate.PersianDate().initGrgDate(year,month,day);persianDate . getMonthDays();
        String y1=String.valueOf(persianDate.getShYear());
        String m1=String.valueOf(persianDate.getShMonth());
        String d1=String.valueOf(persianDate.getShDay());


        if(m1.length()==1)
            m1="0"+m1;

        if(d1.length()==1)
            d1="0"+d1;

        String persianFullDate=y1+"/"+m1+"/"+d1;
        return  persianFullDate;
    }

    public  static Calendar convertTimeWithTimeZome(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));
        cal.setTimeInMillis(time);

        return cal;


    }
}