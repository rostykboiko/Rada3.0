package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Survey implements Parcelable {
    private String surveyID;
    private String surveyTitle;
    private ArrayList<Option> surveyOptionList = new ArrayList<>();
    private boolean surveySingleOption;
    private int duration;
    private int color;
    private int participantsCount;

    public Survey(){}

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
        System.out.println("String array list " + surveyOptionList);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Survey>() {
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public ArrayList<Option> getSurveyOptionList() {
        System.out.println("get list" + surveyOptionList);
        return surveyOptionList;
    }

    private Survey(Parcel in) {
        surveyID = in.readString();
        surveyTitle = in.readString();
        color = in.readInt();
        duration = in.readInt();
        surveySingleOption = in.readByte() != 0;
        //  in.readTypedList(surveyOptionList, CREATOR);
//        surveyOptionList = in.readArrayList();
//        participantsEmailList = in.readArrayList(null);
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public boolean isSurveySingleOption() {
        return surveySingleOption;
    }

    public void setSurveySingleOption(boolean surveySingleOption) {
        this.surveySingleOption = surveySingleOption;
    }
}
