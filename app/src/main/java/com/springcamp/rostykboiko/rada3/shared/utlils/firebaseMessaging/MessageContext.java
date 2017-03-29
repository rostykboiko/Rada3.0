package com.springcamp.rostykboiko.rada3.shared.utlils.firebaseMessaging;

public class MessageContext {
    private String surveyID;
    private String surveyTitle;
    private String surveyBody;

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyBody() {
        return surveyBody;
    }

    public void setSurveyBody(String surveyBody) {
        this.surveyBody = surveyBody;
    }
}
