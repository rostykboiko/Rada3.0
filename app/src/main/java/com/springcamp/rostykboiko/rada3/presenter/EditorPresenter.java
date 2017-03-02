package com.springcamp.rostykboiko.rada3.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.springcamp.rostykboiko.rada3.EditorContract;
import com.springcamp.rostykboiko.rada3.MainActivity;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.data.EditorInteractor;
import com.springcamp.rostykboiko.rada3.data.EditorUseCase;
import com.springcamp.rostykboiko.rada3.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.view.SettingsActivity;

public class EditorPresenter implements EditorContract.Presenter{

    private RecyclerView recycler;

    @Nullable
    private EditorContract.View view;

    @Nullable
    private EditorUseCase editorUseCase;

    public EditorPresenter(@Nullable EditorContract.View view) {
        this.view = view;
        this.editorUseCase = new EditorInteractor();
    }


    @Override
    public void getSurvey(){
        view.showProgress();
        editorUseCase.getSurvey(view.getSurveyTitle(), view.getOptionsList(), new EditorUseCase.EditorCallback(){
            @Override
            public void success() {

            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void onStart(){}

    @Override
    public void onStop() {
        view = null;
    }

    private void setupRecycler(Context context) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
//        adapter = new EventAdapter(this);
//        recycler.setAdapter(adapter);
    }

}
