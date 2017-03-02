package com.springcamp.rostykboiko.rada3;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public interface EditorContract {

    interface View {
        String getId();

        String getSurveyTitle();

        ArrayList<String> getOptionsList();

        void showProgress();
    }

    interface Presenter {
        void getSurvey();

        void onStart();

        void onStop();
    }
}
