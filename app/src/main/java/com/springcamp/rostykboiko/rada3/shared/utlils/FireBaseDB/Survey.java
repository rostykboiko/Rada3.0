package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import java.util.ArrayList;

public class Survey {
    private String surveyID;
    private String surveyTitle;
    private ArrayList<Option> surveyOptionList = new ArrayList<>();
    private boolean surveySingleOption;
    private int duration;
    private int participantsCount;
    private String creatorId;

    public Survey() {
    }

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

    public void setSurveyOptionList(ArrayList<Option> surveyOptionList) {
        this.surveyOptionList = surveyOptionList;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(surveyID);
    }

    @Override
    public boolean equals(Object obj) {
        return surveyID.equals(((Survey) obj).surveyID);
    }
}
