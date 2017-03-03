package com.springcamp.rostykboiko.rada3.adapter;

import java.util.ArrayList;

public class Survey {
    private String syrveyTitle;
    private ArrayList<String> surveyOptionList;
    private boolean surveyPublic;
    private boolean surveySingleOption;

    public Survey(String syrveyTitle,
                  ArrayList<String> surveyOptionList,
                  boolean surveyPublic,
                  boolean surveySingleOption,
                  int duration,
                  ArrayList<String> partiosipantsEmailList) {
        this.syrveyTitle = syrveyTitle;
        this.surveyOptionList = surveyOptionList;
        this.surveyPublic = surveyPublic;
        this.surveySingleOption = surveySingleOption;
        this.duration = duration;
        this.partiosipantsEmailList = partiosipantsEmailList;
    }

    private int duration;
    private ArrayList<String> partiosipantsEmailList;


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

    public boolean isSurveyPublic() {
        return surveyPublic;
    }

    public void setSurveyPublic(boolean surveyPublic) {
        this.surveyPublic = surveyPublic;
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

    public ArrayList<String> getPartiosipantsEmailList() {
        return partiosipantsEmailList;
    }

    public void setPartiosipantsEmailList(ArrayList<String> partiosipantsEmailList) {
        this.partiosipantsEmailList = partiosipantsEmailList;
    }
}
