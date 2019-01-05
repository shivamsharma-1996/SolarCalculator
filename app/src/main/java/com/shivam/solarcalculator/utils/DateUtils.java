package com.shivam.solarcalculator.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shivam Sharma on 04-01-2019.
 */
public class DateUtils {

    private static final String TAG = "DateUtils";

    private DateUtils() {
        // This utility class is not publicly instantiable
    }

    public static String getCalenderDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        return date;
    }

    public static com.shivam.solarcalculator.data.models.Date getCustomDateObject(String inputDate) {
        com.shivam.solarcalculator.data.models.Date outputDateObj = new com.shivam.solarcalculator.data.models.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        Date parse = null;
        try {
            parse = sdf.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        outputDateObj.setDay(calendar.get(Calendar.DATE));
        outputDateObj.setMonth(calendar.get(Calendar.MONTH) + 1);
        outputDateObj.setMonth(calendar.get(Calendar.YEAR));
        return outputDateObj;
    }

    public static String getPreviousDate(String currentDate) {
        String previousDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            Date myDate = dateFormat.parse(currentDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            previousDate = dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "getPreviousDate:" + e.getMessage());
        }
        return previousDate;
    }

    public static String getNextDate(String currentDate) {
        String previousDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            Date myDate = dateFormat.parse(currentDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            previousDate = dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "getPreviousDate:" + e.getMessage());
        }
        return previousDate;
    }
}
