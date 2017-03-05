package com.springcamp.rostykboiko.rada3.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class MainInteractor implements MainUseCase{

    @Override
    public void getSurvey(@NonNull String title,
                          @NonNull ArrayList<String> ptionsList,
                          @NonNull EditorUseCase.EditorCallback callback){
        callback.success();

    }
}
