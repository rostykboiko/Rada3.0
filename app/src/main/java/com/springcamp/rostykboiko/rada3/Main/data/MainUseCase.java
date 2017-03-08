package com.springcamp.rostykboiko.rada3.Main.data;

import android.support.annotation.NonNull;

import com.springcamp.rostykboiko.rada3.Editor.data.EditorUseCase;

import java.util.ArrayList;

interface MainUseCase {
    void getSurvey(@NonNull String title,
                   @NonNull ArrayList<String> ptionsList,
                   @NonNull EditorUseCase.EditorCallback callback);

    interface EditorCallback {

        void success();

        void error();
    }
}
