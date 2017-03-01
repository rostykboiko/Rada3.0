package com.springcamp.rostykboiko.rada3;

import java.util.ArrayList;

public interface EditorContract {

    interface View {
        String getId();

        String getTitle();

        ArrayList<String> getOptionsList();

        void createNewsurvey();

        void editsurvey();

        void showProgress();
    }

    interface Presenter {
        void onStart();

        void onStop();
    }
}
