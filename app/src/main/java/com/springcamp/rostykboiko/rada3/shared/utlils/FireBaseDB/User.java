package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String userName;
    private String userEmail;
    private String userID;
    private String deviceToken;
    private List<String> surveysList = new ArrayList<>();
    private String userProfileIcon;

    public User() {
    }

    public List<String> getSurveysList() {
        return surveysList;
    }

    public void setSurveysList(List<String> surveysList) {
        this.surveysList = surveysList;
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

    public String getUserProfileIcon() {
        return userProfileIcon;
    }

    public void setUserProfileIcon(String userProfileIcon) {
        this.userProfileIcon = userProfileIcon;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(userName);
        out.writeString(userEmail);
        out.writeString(userID);
        out.writeString(deviceToken);
        out.writeString(userProfileIcon);
        out.writeList(surveysList);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


    private User(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userID = in.readString();
        deviceToken = in.readString();
        userProfileIcon = in.readString();
        surveysList = in.createStringArrayList();
    }
}
