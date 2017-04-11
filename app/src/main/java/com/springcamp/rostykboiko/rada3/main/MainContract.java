package com.springcamp.rostykboiko.rada3.main;

import android.support.annotation.NonNull;

import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

public interface MainContract {

    interface View {
        void showProgress();

        void showEditor(@NonNull Survey survey);
    }

    interface Presenter {

        void onStart();

        void showEditor(@NonNull Survey survey);

        void onStop();
    }
}
