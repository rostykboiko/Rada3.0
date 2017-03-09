package com.springcamp.rostykboiko.rada3.shared.utlils;

import android.graphics.Bitmap;
import android.net.Uri;

public class GoogleAccountAdapter {

    private String personId = null;
    private String userName = null;
    private String userEmail = null;
    private String userID = null;
    private Uri userProfileIcon;
    public static Bitmap bm = null;

    public GoogleAccountAdapter() {
    }

    public GoogleAccountAdapter(
            String personId,
            String userName,
            String userEmail,
            String userID,
            Uri userProfileIcon) {
        this.personId = personId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userID = userID;
        this.userProfileIcon = userProfileIcon;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userAuthName) {
        userName = userAuthName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userAuthEmail) {
        userEmail = userAuthEmail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userAuthID) {
        userID = userAuthID;
    }

    public void setProfileIcon(Uri userAuthIcon) {
        this.userProfileIcon = userAuthIcon;
    }

    public Uri getProfileIcon() {
        return userProfileIcon;
    }

    public void logOut() {
        personId = null;
        userName = null;
        userEmail = null;
        userID = null;
        userProfileIcon = null;
    }
}