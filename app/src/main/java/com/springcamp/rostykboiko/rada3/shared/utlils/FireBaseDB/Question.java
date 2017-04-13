package com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB;

import java.util.ArrayList;

public class Question {
    private String surveyId;
    private String surveyTitle;
    private ArrayList<Option> options = new ArrayList<>();
    private ArrayList<String> answersList = new ArrayList<>();
    private int duration;

    public Question(String surveyId, String surveyTitle, ArrayList<Option> options, ArrayList<String> answersList, int duration) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.options = options;
        this.answersList = answersList;
        this.duration = duration;
    }

}
