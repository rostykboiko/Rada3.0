package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Survey implements Parcelable {
    private String surveyID;
    private String surveyTitle;
    private ArrayList<String> surveyOptionList = new ArrayList<>();
    private boolean surveySingleOption;
    private int duration;
    private ArrayList<User> participantsEmailList;
    private int color;

    public Survey(){}

    public Survey(String surveyID, String surveyTitle,
                  ArrayList<String> surveyOptionList,
                  boolean surveySingleOption,
                  int duration,
                  ArrayList<User> participantsEmailList,
                  int color) {
        this.surveyID = surveyID;
        this.surveyTitle = surveyTitle;
        this.surveyOptionList = surveyOptionList;
        this.surveySingleOption = surveySingleOption;
        this.duration = duration;
        this.participantsEmailList = participantsEmailList;
        this.color = color;
    }


    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public ArrayList<String> getSurveyOptionList() {
        return surveyOptionList;
    }

// Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(surveyID);
        out.writeString(surveyTitle);
        out.writeList(surveyOptionList);
        out.writeByte((byte) (surveySingleOption ? 1 : 0));
        out.writeInt(duration);
        out.writeInt(color);
        out.writeList(participantsEmailList);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Survey> CREATOR = new Parcelable.Creator<Survey>() {
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Survey(Parcel in) {
        surveyID = in.readString();
        surveyTitle = in.readString();
        color = in.readInt();
        duration = in.readInt();
        surveySingleOption = in.readByte() != 0;
        surveyOptionList = in.readArrayList(null);
        participantsEmailList = in.readArrayList(null);
    }
}
