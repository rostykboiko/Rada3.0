package com.springcamp.rostykboiko.rada3.shared.utlils;

import android.app.Activity;
import android.content.Intent;

import com.springcamp.rostykboiko.rada3.R;

import java.util.Calendar;

public class Utils {

    public static int longToInt(long longNumber) {
        int intNumber = (int) longNumber;
        if ((long) intNumber != longNumber) {
            throw new IllegalArgumentException(longNumber + " cannot be cast to int without changing its value.");
        }
        return intNumber;
    }

    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_YELLOW = 1;
    public final static int THEME_BLUE = 2;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppThemeLight);
                break;
            case THEME_YELLOW:
                activity.setTheme(R.style.AppThemeYellow);
                break;
            case THEME_BLUE:
                //      activity.setTheme(R.style.Thirdheme);
                break;
        }
    }

    public static int durationStringToSec(String durationString) {
        int duration;
        String[] time;

        time = durationString.split(":");
        duration = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);

        return duration;
    }
    public static String timeFormat(int duration) {
        String formated;

        if (duration < 10) {
            formated = "0" + duration;
        } else {
            formated = "" + duration;
        }

        return formated;
    }


    public static String timePickerConvert(int minutes, int seconds) {
        String timer = "00:00";

        if (minutes < 10) {
            timer = "0" + minutes + ":";
        } else {
            timer = minutes + ":";
        }

        if (seconds < 10) {
            timer += "0" + seconds;
        } else {
            timer += seconds;
        }
        return timer;
    }

    public static String setSurveyEnd(String surveyEnd) {
        // surveyEnd format - 00:00
        // calendarTime format - Wed May 24 19:00:25 GMT+03:00 2017
        Calendar calendar = Calendar.getInstance();

        int minutes;
        int seconds;

        String[] part = surveyEnd.split(":");
        minutes = Integer.parseInt(part[0]);
        seconds = Integer.parseInt(part[1]);

        calendar = Calendar.getInstance();
        System.out.println("calendarTime " + calendar.getTime());
        String[] calendarTimeSplit = calendar.getTime().toString().split(" ");

        calendarTimeSplit[3] = calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                (calendar.get(Calendar.MINUTE) + minutes) + ":" +
                (calendar.get(Calendar.SECOND) + seconds);

        calendarTimeSplit = setTimeLimit(calendarTimeSplit);

        String time = "";
        for (String string : calendarTimeSplit) {
            if (string.equals("2017")) {
                time += string;
            } else {
                time += string + " ";
            }
        }
        System.out.println("calendarTime time " + time);

        return time;
    }

    public static String[] setTimeLimit(String[] date) {

        String[] time = date[3].split(":");
        int seconds = Integer.parseInt(time[2]);
        int minutes = Integer.parseInt(time[1]);
        int hours = Integer.parseInt(time[0]);

        if (seconds > 60) {
            minutes += seconds / 60;
            time[2] = seconds % 60 + "";
        } else {
            time[2] = timeFormat(seconds);
        }
        if (minutes > 60) {
            hours += minutes / 60;
            time[1] = minutes % 60 + "";
        } else {
            time[1] = timeFormat(minutes);
        }
        if (hours > 24) {
            date[2] = Integer.parseInt(date[2]) + (hours / 24) + "";
            time[0] = hours % 24 + "";
        } else {
            time[0] = timeFormat(hours);
        }

        date[3] = "";
        for (int index = 0; index < 3; index++) {
            if (index != 2) {
                date[3] += time[index] + ":";
            } else {
                date[3] += time[index];
            }
        }

        return date;
    }
}
