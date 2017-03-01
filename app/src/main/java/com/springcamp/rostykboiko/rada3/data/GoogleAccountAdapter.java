package com.springcamp.rostykboiko.rada3.data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

public class GoogleAccountAdapter {

    private static String userName = null;
    private static String userEmail = null;
    private static String userID = null;
    private static Uri userProfileIcon;
    public static Bitmap bm = null;

    public static String getUserName(){
        return userName;
    }
    public static void setUserName(String userAuthName) {
        userName = userAuthName;
    }

    public static String getUserEmail(){
        return userEmail;
    }
    public static void setUserEmail(String userAuthEmail){
        userEmail = userAuthEmail;
    }

    public static String getUserID(){
        return userID;
    }
    public static void setUserID(String userAuthID){
        userID = userAuthID;
    }

    public static void setProfileIcon(Uri userAuthIcon){
        userProfileIcon = userAuthIcon;
    }

    public static Uri getProfileIcon(){
        return userProfileIcon;
    }

    public static void logOut(){
        userName = null;
        userEmail = null;
        userID = null;
        userProfileIcon = null;
    }
}