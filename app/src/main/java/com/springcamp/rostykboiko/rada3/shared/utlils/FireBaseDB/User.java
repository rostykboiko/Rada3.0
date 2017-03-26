package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import android.net.Uri;

public class User {
    private String userName;
    private String userEmail;
    private String userID;
    private static Uri userProfileIcon;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Uri getUserProfileIcon() {
        return userProfileIcon;
    }

    public static void setUserProfileIcon(Uri userProfileIcon) {
        User.userProfileIcon = userProfileIcon;
    }
}
