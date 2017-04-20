package com.springcamp.rostykboiko.rada3.answer;

import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;

public interface AnswerContract {

    interface View {
        int getPosition();

        Survey getSurvey();

        SessionManager getSession();

        ArrayList<Option> getCheckedOptionsList();
    }

    interface Presenter {

        void submitAnswer();

        void addCheckedItem();

        void deleteCheckedItem();

        void onStart();

        void onStop();
    }
}
