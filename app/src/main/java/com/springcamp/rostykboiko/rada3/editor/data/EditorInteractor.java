package com.springcamp.rostykboiko.rada3.editor.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class EditorInteractor implements EditorUseCase{

    @Override
    public void getSurvey(@NonNull String title,
                          @NonNull ArrayList<String> ptionsList,
                          @NonNull EditorUseCase.EditorCallback callback){
        callback.success();

    }
}
