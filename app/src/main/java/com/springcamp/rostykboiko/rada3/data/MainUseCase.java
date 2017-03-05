package com.springcamp.rostykboiko.rada3.data;

import android.support.annotation.NonNull;

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
