package com.springcamp.rostykboiko.rada3.main.presenter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.springcamp.rostykboiko.rada3.main.data.MainUseCase;
import com.springcamp.rostykboiko.rada3.main.data.MainInteractor;
import com.springcamp.rostykboiko.rada3.MainContract;
import com.springcamp.rostykboiko.rada3.R;

import butterknife.BindView;

public class MainPresenter implements MainContract.Presenter {

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
