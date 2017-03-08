package com.springcamp.rostykboiko.rada3.Main.data;

import android.support.annotation.NonNull;

import com.springcamp.rostykboiko.rada3.Editor.data.EditorUseCase;

import java.util.ArrayList;

public class MainInteractor implements MainUseCase{

    @Override
    public void getSurvey(@NonNull String title,
                          @NonNull ArrayList<String> ptionsList,
                          @NonNull EditorUseCase.EditorCallback callback){
        callback.success();

    }
}
