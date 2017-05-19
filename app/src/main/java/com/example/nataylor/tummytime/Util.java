package com.example.nataylor.tummytime;


import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nataylor on 5/4/17.
 */

public class Util {
    public static String convertTimeToString (float time) {
        String convertedTime;
        long sec = (long)(time/1000);
        long min = (long)((time/1000)/60);
        long hr = (long)(((time/1000)/60)/60);

        // Convert seconds to string
        sec = sec % 60;
        String second = String.valueOf(sec);
        if(sec == 0){
            second = "00";
        }
        if(sec <10 && sec > 0){
            second = "0"+second;
        }

        // Convert minutes to string
        min = min % 60;
        String minute = String.valueOf(min);
        if(min == 0){
            minute = "00";
        }
        if(min <10 && min > 0){
            minute = "0"+minute;
        }

        // Convert hours to string
        String hour = String.valueOf(hr);
        if(hr == 0){
            hour = "00";
        }
        if(hr <10 && hr > 0) {
            hour = "0" + hour;
        }

        if(hour == "00"){
            convertedTime = minute + ":" + second;
        }
        else {
            convertedTime = hour + ":" + minute + ":" + second;
        }

        return convertedTime;
    }

    public static String convertStartTimeToString (long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String convertedTime =  sdf.format(c.getTime());

        return convertedTime;
    }
}
