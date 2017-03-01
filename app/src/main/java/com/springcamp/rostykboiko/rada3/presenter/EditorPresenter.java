package com.springcamp.rostykboiko.rada3.presenter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.springcamp.rostykboiko.rada3.EditorContract;
import com.springcamp.rostykboiko.rada3.MainActivity;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.data.EditorUseCase;
import com.springcamp.rostykboiko.rada3.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.view.SettingsActivity;

public class EditorPresenter implements EditorContract.Presenter{

    @Nullable
    private EditorContract.View view;

    @Nullable
    private EditorUseCase editorUseCase;

    public EditorPresenter(@Nullable EditorContract.View view, EditorUseCase editorUseCase) {
        this.view = view;
        this.editorUseCase = editorUseCase;
    }

    @Override
    public void onStart(){}

    @Override
    public void onStop() {
        view = null;
    }

}
