package com.springcamp.rostykboiko.rada3.answer;

import android.support.annotation.Nullable;

import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

public interface AnswerContract {

    interface View {
        int getPosition();

        Survey getSurvey();

        SessionManager getSession();
    }

    interface Presenter {

        void submitAnswer();

        void addCheckedItem(@Nullable int position,
                            @Nullable Survey survey);

        void deleteCheckedItem(@Nullable int position,
                               @Nullable Survey survey);

        void onStart();

        void onStop();
    }
}
