package com.springcamp.rostykboiko.rada3.Editor.presenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.springcamp.rostykboiko.rada3.EditorContract;
import com.springcamp.rostykboiko.rada3.Editor.data.EditorInteractor;
import com.springcamp.rostykboiko.rada3.Editor.data.EditorUseCase;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.OptionListAdapter;

import java.util.ArrayList;


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
        view.showProgress();
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
    public OptionListAdapter initOptionListAdapter(ArrayList<String> optionsList) {
        OptionListAdapter optionsAdapter = new OptionListAdapter(optionsList);

        return optionsAdapter;
    }

    @Override
    public RecyclerView initOptionsListView(Context mContext, EditorContract.View view, OptionListAdapter optionListAdapter) {
        RecyclerView optionsRecycler = (RecyclerView) view.findViewById(R.id.option_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
        optionsRecycler.setLayoutManager(mLayoutManager);
        optionsRecycler.setItemAnimator(new DefaultItemAnimator());
        optionsRecycler.setAdapter(optionListAdapter);

        return optionsRecycler;
    }

    @Override
    public void onStart() {
    }


    @Override
    public void onStop() {
        view = null;
    }
}
