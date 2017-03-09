package com.springcamp.rostykboiko.rada3.editor.data;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public interface EditorUseCase {

    void getSurvey(@NonNull String title,
                   @NonNull ArrayList<String> ptionsList,
                   @NonNull EditorUseCase.EditorCallback callback);

    interface EditorCallback {

        void success();

        void error();
    }
}
