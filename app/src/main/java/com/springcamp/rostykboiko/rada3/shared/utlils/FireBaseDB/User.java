package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import android.net.Uri;

class User {
    private static String userName;
    private static String userEmail;
    private static String userID;
    private static Uri userProfileIcon;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        User.userEmail = userEmail;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        User.userID = userID;
    }

    public static Uri getUserProfileIcon() {
        return userProfileIcon;
    }

    public static void setUserProfileIcon(Uri userProfileIcon) {
        User.userProfileIcon = userProfileIcon;
    }
}
