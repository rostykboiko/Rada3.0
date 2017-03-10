package com.springcamp.rostykboiko.rada3.intro.data;

import android.support.annotation.NonNull;

public class IntroInteractor implements IntroUseCase {

    @Override
    public void getSurvey(@NonNull IntroUseCase.IntroCallback callback){
        callback.success();

    }
}
