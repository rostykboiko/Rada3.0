package com.springcamp.rostykboiko.rada3.intro.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

interface IntroUseCase {

    void getSurvey(@NonNull IntroUseCase.IntroCallback callback);

    interface IntroCallback {

        void success();

        void error();
    }
}
