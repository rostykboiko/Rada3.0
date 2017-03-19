package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import java.util.ArrayList;
import java.util.List;

public class Survey {
    private String surveyID;
    private String surveyTitle;
    private List<String> surveyOptionList = new ArrayList<>();
    private boolean surveySingleOption;
    private int duration;
    private List<User> participantsEmailList;
    private int color;

    public Survey(){}
    public Survey(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public Survey(String surveyTitle, List<String> surveyOptionList) {
        this.surveyTitle = surveyTitle;
        this.surveyOptionList = surveyOptionList;
    }

    public Survey(String surveyID, String surveyTitle,
                  List<String> surveyOptionList,
                  boolean surveySingleOption,
                  int duration,
                  List<User> participantsEmailList,
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

    public List<String> getSurveyOptionList() {
        return surveyOptionList;
    }

    public void setSurveyOptionList(List<String> surveyOptionList) {
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

    public List<User> getPartiosipantsEmailList() {
        return participantsEmailList;
    }

    public void setPartiosipantsEmailList(List<User> participantsEmailList) {
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
