package com.springcamp.rostykboiko.rada3;


import java.util.ArrayList;

public interface MainContract {

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
