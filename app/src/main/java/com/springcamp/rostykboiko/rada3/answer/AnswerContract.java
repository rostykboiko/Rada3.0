package com.springcamp.rostykboiko.rada3.answer;

import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;

public interface AnswerContract {

    interface View {
        int getPosition();

        String getSurveyId();

        SessionManager getSession();

        ArrayList<Option> getOptionsList();

        ArrayList<Option> getAdaptorOptionsList();
    }

    interface Presenter {

        void submitAnswer();

        void addCheckedItem();

        void deleteCheckedItem();

        void onStart();

        void onStop();
    }
}
