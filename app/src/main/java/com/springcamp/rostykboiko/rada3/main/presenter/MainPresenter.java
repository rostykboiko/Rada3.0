package com.springcamp.rostykboiko.rada3.main.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.springcamp.rostykboiko.rada3.main.data.MainUseCase;
import com.springcamp.rostykboiko.rada3.main.data.MainInteractor;
import com.springcamp.rostykboiko.rada3.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.shared.utlils.OptionListAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.Survey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainPresenter implements MainContract.Presenter {
    @Nullable
    private CardsAdaptor cardsAdaptor;
    @Nullable
    private OptionListAdapter optionsAdapter;

    @Nullable
    private MainContract.View view;

    @Nullable
    private MainUseCase mainUseCase;

    @BindView(R.id.card_recycler)
    RecyclerView recyclerView;

    public MainPresenter(@Nullable MainContract.View view) {
        this.view = view;
        this.mainUseCase = new MainInteractor();
    }



    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
