package com.springcamp.rostykboiko.rada3.editor.presenter;

import android.support.annotation.Nullable;
import com.springcamp.rostykboiko.rada3.editor.EditorContract;
import com.springcamp.rostykboiko.rada3.editor.data.EditorInteractor;
import com.springcamp.rostykboiko.rada3.editor.data.EditorUseCase;

public class EditorPresenter implements EditorContract.Presenter {

    @Nullable
    private EditorContract.View view;

    @Nullable
    private EditorUseCase editorUseCase;

    public EditorPresenter(@Nullable EditorContract.View view) {
        this.view = view;
        this.editorUseCase = new EditorInteractor();
    }

    @Override
    public void getSurvey() {
        editorUseCase.getSurvey(view.getSurveyTitle(), view.getOptionsList(), new EditorUseCase.EditorCallback() {
            @Override
            public void success() {

            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        view = null;
    }
}
