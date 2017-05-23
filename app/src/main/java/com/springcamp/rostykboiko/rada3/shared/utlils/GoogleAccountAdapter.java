package com.springcamp.rostykboiko.rada3.shared.utlils;

public class GoogleAccountAdapter {
    private static String userName = null;
    private static String userEmail = null;
    private static String accountID = null;
    private static String deviceToken = null;
    private static String userProfileIcon = null;

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

    public static void setProfileIcon(String userAuthIcon) {
        userProfileIcon = userAuthIcon;
    }

    public static String getProfileIcon() {
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

    public static void logOut() {
        userName = null;
        userEmail = null;
        userProfileIcon = null;
    }
}