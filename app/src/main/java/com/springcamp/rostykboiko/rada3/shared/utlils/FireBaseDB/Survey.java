package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import java.util.ArrayList;

public class Survey {
    private String surveyID;
    private String syrveyTitle;
    private ArrayList<String> surveyOptionList;
    private boolean surveySingleOption;
    private int duration;
    private ArrayList<User> participantsEmailList;
    private int color;

    public Survey(String syrveyTitle) {
        this.syrveyTitle = syrveyTitle;
    }

    public Survey(String syrveyTitle, ArrayList<String> surveyOptionList) {
        this.syrveyTitle = syrveyTitle;
        this.surveyOptionList = surveyOptionList;
    }

    public Survey(String surveyID, String syrveyTitle,
                  ArrayList<String> surveyOptionList,
                  boolean surveySingleOption,
                  int duration,
                  ArrayList<User> participantsEmailList,
                  int color) {
        this.surveyID = surveyID;
        this.syrveyTitle = syrveyTitle;
        this.surveyOptionList = surveyOptionList;
        this.surveySingleOption = surveySingleOption;
        this.duration = duration;
        this.participantsEmailList = participantsEmailList;
        this.color = color;
    }


    public String getSyrveyTitle() {
        return syrveyTitle;
    }

    public void setSyrveyTitle(String syrveyTitle) {
        this.syrveyTitle = syrveyTitle;
    }

    public ArrayList<String> getSurveyOptionList() {
        return surveyOptionList;
    }

    public void setSurveyOptionList(ArrayList<String> surveyOptionList) {
        this.surveyOptionList = surveyOptionList;
    }

    public boolean isSurveySingleOption() {
        return surveySingleOption;
    }

    public void setSurveySingleOption(boolean surveySingleOption) {
        this.surveySingleOption = surveySingleOption;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<User> getPartiosipantsEmailList() {
        return participantsEmailList;
    }

    public void setPartiosipantsEmailList(ArrayList<User> participantsEmailList) {
        this.participantsEmailList = participantsEmailList;
    }

    public int getSurveyColor() {
        return color;
    }

    public void setSurveyColor(int color) {
        this.color = color;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }
}
