package com.springcamp.rostykboiko.rada3.main.data;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public interface MainUseCase {
    void getSurvey(@NonNull String title,
                   @NonNull ArrayList<String> ptionsList,
                   @NonNull MainUseCase.EditorCallback callback);

    interface EditorCallback {

        void success();

        void error();
    }
}
