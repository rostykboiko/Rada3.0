package com.springcamp.rostykboiko.rada3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.springcamp.rostykboiko.rada3.api.model.Question;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

public class QuestionReceiver extends BroadcastReceiver {
    private static final String SURVEY_KEY = "SURVEY_KEY";
    public static final String QUESTION_RECEIVED_FILTER = "QUESTION_RECEIVED_FILTER";

    @NonNull
    private QuestionReceivedCallback callback;

    public QuestionReceiver(@NonNull QuestionReceivedCallback callback){
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String json = intent.getStringExtra(SURVEY_KEY);
        Survey survey = new Gson().fromJson(json, Survey.class);
        callback.onQuestionReceived(survey);
    }

    public interface QuestionReceivedCallback {
        void onQuestionReceived(@NonNull Survey survey);
    }
}
