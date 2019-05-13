package rayagruops.com.abfa.utils;

import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

public class DateHepler {
  public static String getEnglishMonthName(int month) {
    String monthName = null;
    switch (month) {
      case 1:
        monthName = "فروردین";
        break;
      case 2:
        monthName = "اردیبهشت";
        break;
      case 3:
        monthName = "خرداد";
        break;
      case 4:
        monthName = "تیر";
        break;
      case 5:
        monthName = "مرداد";
        break;
      case 6:
        monthName = "شهریور";
        break;
      case 7:
        monthName = "مهر";
        break;
      case 8:
        monthName = "آبان";
        break;
      case 9:
        monthName = "آذر";
        break;
      case 10:
        monthName = "دی";
        break;
      case 11:
        monthName = "بهمن";
        break;
      case 12:
        monthName = "اسفند";
        break;
    }
    return monthName;
  }

  public static String intToStringDayOfWeek(int day) {
    String s = null;
    switch (day) {
      case 1:
        s = "یکشنبه";
        break;
      case 2:
        s = "دوشنبه";
        break;
      case 3:
        s = "سه شنبه";
        break;
      case 4:
        s = "چهارشنبه";
        break;
      case 5:
        s = "پنجشنبه";
        break;
      case 6:
        s = "جمعه";
        break;
      case 7:
        s = "شنبه";
        break;
    }
    return s;
  } public  static Calendar convertTimeWithTimeZome(long time){
    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));
    cal.setTimeInMillis(time*1000);

    return cal;

/*
        return (cal.get(Calendar.YEAR) + " " + (cal.get(Calendar.MONTH) + 1) + " "
                + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":"
                + cal.get(Calendar.MINUTE));*/

  }  public static Calendar getCurrentDate(){
    Calendar rightNow = Calendar.getInstance();

    rightNow.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));



    return rightNow;

  }
  public static String[] getDate(long date){
    String[]data=new String[2];
    Calendar serverDate=   convertTimeWithTimeZome(date);
    Calendar rightNow=   getCurrentDate();
    int serverYear=serverDate.get(Calendar.YEAR);
    int serverMonth=serverDate.get(Calendar.MONTH)+1;
    int serverDay=serverDate.get(Calendar.DAY_OF_MONTH);

    int nowYear=rightNow.get(Calendar.YEAR);
    int nowMonth=rightNow.get(Calendar.MONTH)+1;
    int nowDay=rightNow.get(Calendar.DAY_OF_MONTH);

/*

Log.e("date=",serverDate.get(Calendar.YEAR) + " " + (serverDate.get(Calendar.MONTH) + 1) + " "
                + serverDate.get(Calendar.DAY_OF_MONTH) + " " + serverDate.get(Calendar.HOUR_OF_DAY) + ":"
                + serverDate.get(Calendar.MINUTE));
*/


    if(serverYear==nowYear&&serverMonth==nowMonth&&serverDay==nowDay){
      data[0]="امروز";

    }
   else if(serverYear==nowYear&&serverMonth==nowMonth&&serverDay==nowDay+1){
      data[0]="فردا";

    }
    else if(serverYear==nowYear&&serverMonth==nowMonth&&serverDay==nowDay-1){
      data[0]="دیروز";

    }
    else {

      String persianDate = convertGregorianToPersiabDate(serverYear,serverMonth,serverDay);
      //  data[0]=serverYear+"/"+serverMonth+"/"+serverDay;
      data[0]=persianDate;

    }

    String h1= String.valueOf(serverDate.get(Calendar.HOUR_OF_DAY));
    String m1= String.valueOf(serverDate.get(Calendar.MINUTE));

    if(h1.length()==1)
      h1="0"+h1;

    if(m1.length()==1)
      m1="0"+m1;


    data[1]= h1 + ":"
            + m1;


    return data;
  }
  public static String AM_PM(float hour) {
    String am_pm;
     if (hour < 12) {
      am_pm = "قبل از ظهر";
      return am_pm;
    }
    if (hour >= 12) {
      am_pm = "بعد از ظهر";
      return am_pm;
    }
    return " ";
  }  private static String convertGregorianToPersiabDate(int year, int month, int day){

    saman.zamani.persiandate.PersianDate persianDate=new saman.zamani.persiandate.PersianDate().initGrgDate(year,month,day);

    String y1= String.valueOf(persianDate.getShYear());
    String m1= String.valueOf(persianDate.getShMonth());
    String d1= String.valueOf(persianDate.getShDay());


/*if(y1.length()==1)
    y1="0"+y1;*/

    if(m1.length()==1)
      m1="0"+m1;

    if(d1.length()==1)
      d1="0"+d1;

    String persianFullDate=y1+"/"+m1+"/"+d1;
    return  persianFullDate;
  }

}
