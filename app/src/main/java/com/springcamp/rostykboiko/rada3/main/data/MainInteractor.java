package com.springcamp.rostykboiko.rada3.main.data;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public class MainInteractor implements MainUseCase {

    @Override
    public void getSurvey(@NonNull String title,
                          @NonNull ArrayList<String> ptionsList,
                          @NonNull MainUseCase.EditorCallback callback) {
        callback.success();

    }
}
