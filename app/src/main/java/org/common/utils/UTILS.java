package org.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by amd on 8/27/15.
 */
public class UTILS {

    public static boolean NotNullNotEmpty(String... params) {
        for(String p:params){
            if(p==null || p.trim().equals(""))
                return false;
        }
        return true;
    }

    public static boolean isValidDate(String date, String formatter){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        simpleDateFormat.setLenient(false);
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        try{
            Date date1 = simpleDateFormat.parse(date);
            calendar.setTime(date1);
            calendar.getTime();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
