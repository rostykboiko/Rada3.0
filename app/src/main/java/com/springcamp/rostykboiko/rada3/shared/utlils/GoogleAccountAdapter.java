package com.springcamp.rostykboiko.rada3.shared.utlils;

import android.net.Uri;

public class GoogleAccountAdapter {
    private static String userName = null;
    private static String userEmail = null;
    private static String accountID = null;
    private static String userID = null;
    private static String deviceToken = null;
    private static Uri userProfileIcon;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userAuthName) {
        userName = userAuthName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userAuthEmail) {
        userEmail = userAuthEmail;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userAuthID) {
        userID = userAuthID;
    }

    public static void setProfileIcon(Uri userAuthIcon) {
        userProfileIcon = userAuthIcon;
    }

    public static Uri getProfileIcon() {
        return userProfileIcon;
    }

    public static String getAccountID() {
        return accountID;
    }

    public static void setAccountID(String accountID) {
        GoogleAccountAdapter.accountID = accountID;
    }

    public static String getDeviceToken() {
        return deviceToken;
    }

    public static void setDeviceToken(String deviceToken) {
        GoogleAccountAdapter.deviceToken = deviceToken;
    }

    public static Uri getUserProfileIcon() {
        return userProfileIcon;
    }

    public static void setUserProfileIcon(Uri userProfileIcon) {
        GoogleAccountAdapter.userProfileIcon = userProfileIcon;
    }
    public static void logOut() {
        userName = null;
        userEmail = null;
        userID = null;
        userProfileIcon = null;
    }
}