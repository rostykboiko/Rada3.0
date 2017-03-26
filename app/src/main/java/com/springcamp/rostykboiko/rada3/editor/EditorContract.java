package com.springcamp.rostykboiko.rada3.editor;

import java.util.ArrayList;

public interface EditorContract {

    interface View {
        String getId();

        String getSurveyTitle();

        ArrayList<String> getOptionsList();

    }

    interface Presenter {

        void getSurvey();

        void onStart();

        void onStop();
    }
}
