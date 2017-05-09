package com.springcamp.rostykboiko.rada3.shared.utlils;

import android.app.Activity;
import android.content.Intent;

import com.springcamp.rostykboiko.rada3.R;

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


}
