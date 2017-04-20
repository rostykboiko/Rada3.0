package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

public class User {
    private String userName;
    private String userEmail;
    private String userID;
    private String accountID;
    private String deviceToken;
    private String userProfileIcon;

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

}
