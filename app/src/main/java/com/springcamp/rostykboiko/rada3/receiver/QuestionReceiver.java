package com.springcamp.rostykboiko.rada3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.springcamp.rostykboiko.rada3.api.model.Question;

/**
 * Created by rostykboiko on 13.04.2017.
 */

public class QuestionReceiver extends BroadcastReceiver {

    public static final String QUESTION_RECEIVED_FILTER = "QUESTION_RECEIVED_FILTER";

    @NonNull
    private QuestionReceivedCallback callback;

    public QuestionReceiver(@NonNull QuestionReceivedCallback callback){
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        callback.onQuestionReceived(null);
    }

    public interface QuestionReceivedCallback {
        void onQuestionReceived(@NonNull Question question);
    }
}
