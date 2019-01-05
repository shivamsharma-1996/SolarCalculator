package com.shivam.solarcalculator.utils;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.shivam.solarcalculator.data.models.Date;
import com.shivam.solarcalculator.utils.AppConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.inject.Inject;

import static com.shivam.solarcalculator.utils.AppConstants.INDIAN_LOCAL_OFFSET;

/**
 * Created by Shivam Sharma on 04-01-2019.
 */
public class PhaseTimeCalculator {
    private static final String TAG = "PhaseTimeCalculator";

    private int month;
    private int year;
    private int day;
    private static final double D2R = Math.PI / 180;    //degree to radian
    private static final double R2D = 180 / Math.PI;    //radian to degree

    private static final DateFormat INPUT_FORMAT = new SimpleDateFormat("HH:mm");
    private static final DateFormat OUTPUT_FORMAT = new SimpleDateFormat("KK:mm a");

    @Inject
    public PhaseTimeCalculator() {

    }

    public void setDayMonthYear(Date inputDate){
        this.day = inputDate.getDay();
        this.month = inputDate.getMonth();
        this.year = inputDate.getYear();
    }

    public String calcSunrise(Date date, double indianLocalOffset, double latitude, double longitude) {
        this.setDayMonthYear(date);
        return calc(indianLocalOffset, latitude, longitude, true);
    }

    public String calcSunset(Date date, double indianLocalOffset, double latitude, double longitude) {
        this.setDayMonthYear(date);
        return calc(indianLocalOffset, latitude, longitude, false);
    }

    private String calc(double indianLocalOffset, double latitude, double longitude, boolean sunrise) {
        double N, N1, N2, N3;
        double lngHour, t;
        double M;
        double L;
        double RA;
        double Lquadrant, RAquadrant;
        double sinDec, cosDec;
        double cosH, H, zenith = 90.83333333333333;
        double T;
        double UTC;


        //getting dayOfYear vars
        N1 = Math.floor(275 * month / 9);
        N2 = Math.floor((month + 9) / 12);
        N3 = (1 + Math.floor((year - 4 * Math.floor(year / 4) + 2) / 3));
        N = N1 - (N2 * N3) + day - 30;


        //convert the longitude to hour value and calculate an approximate time
        lngHour = longitude / 15;
        if (sunrise == true)
            t = N + ((6 - lngHour) / 24);    //if rising time is desired:
        else
            t = N + ((18 - lngHour) / 24);   //if setting time is desired:


        //calculate the Sun's mean anomaly
        M = (0.9856 * t) - 3.289;


        //calculate the Sun's true longitude
        L = M + (1.916 * Math.sin(M * D2R)) + (0.020 * Math.sin(2 * M * D2R)) + 282.634;
        if (L > 360) {
            L = L - 360;
        } else if (L < 0) {
            L = L + 360;
        }


        //calculate the Sun's right ascension
        RA = R2D * Math.atan(0.91764 * Math.tan(L * D2R));
        if (RA > 360) {
            RA = RA - 360;
        } else if (RA < 0) {
            RA = RA + 360;
        }


        //right ascension value needs to be in the same quadrant as L
        Lquadrant = (Math.floor(L / (90))) * 90;
        RAquadrant = (Math.floor(RA / 90)) * 90;
        RA = RA + (Lquadrant - RAquadrant);


        //right ascension value needs to be converted into hours
        RA = RA / 15;


        //calculate the Sun's declination
        sinDec = 0.39782 * Math.sin(L * D2R);
        cosDec = Math.cos(Math.asin(sinDec));


        //calculate the Sun's local hour angle
        cosH = (Math.cos(zenith * D2R) - (sinDec * Math.sin(latitude * D2R))) / (cosDec * Math.cos(latitude * D2R));
        if (cosH > 1) {
            //the sun never rises on this location (on the specified date)
        } else if (cosH < -1) {
            //the sun never sets on this location (on the specified date)
        }


        //finish calculating H and convert into hours
        if (sunrise) {
            H = 360 - R2D * Math.acos(cosH);
        } else {
            H = R2D * Math.acos(cosH);
        }
        H = H / 15;


        //calculate local mean time of rising/setting
        T = H + RA - (0.06571 * t) - 6.622;


        //adjust back to UTC
        UTC = T - lngHour;
        if (UTC > 24) {
            UTC = UTC - 24;
        } else if (UTC < 0) {
            UTC = UTC + 24;
        }

        //convert UTC value to local INDIAN time zone of latitude/longitude
        UTC = UTC + indianLocalOffset;

        //retrieving hours & minute seperate from double UT
        int hours = (int) UTC;
        int minutes = (int) Math.round((UTC - hours) * 60);

        java.util.Date utcTimeFormat = null;
        try {
            utcTimeFormat = INPUT_FORMAT.parse(String.format("%02d:%02d", hours, minutes));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String utcTime = OUTPUT_FORMAT.format(utcTimeFormat);
        return utcTime;

    }
}
