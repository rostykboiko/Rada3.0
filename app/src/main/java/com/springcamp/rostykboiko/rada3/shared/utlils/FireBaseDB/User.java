package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import java.util.ArrayList;

public class User {
    private String userName;
    private String userEmail;
    private String accountID;
    private String deviceToken;
    private String userProfileIcon;
    private ArrayList<String> userSurveys;

    public User() {
    }

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

    public void setUserProfileIcon(String userProfileIcon) {
        this.userProfileIcon = userProfileIcon;
    }

    public String getUserProfileIcon() {
        return userProfileIcon;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public ArrayList<String> getUserSurveys() {
        return userSurveys;
    }

    public void setUserSurveys(ArrayList<String> userSurveys) {
        this.userSurveys = userSurveys;
    }

}
