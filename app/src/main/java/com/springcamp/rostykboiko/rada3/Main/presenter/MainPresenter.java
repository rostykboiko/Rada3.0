package com.springcamp.rostykboiko.rada3.Main.presenter;

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

import com.springcamp.rostykboiko.rada3.Main.data.MainUseCase;
import com.springcamp.rostykboiko.rada3.Main.data.MainInteractor;
import com.springcamp.rostykboiko.rada3.Main.view.MainActivity;
import com.springcamp.rostykboiko.rada3.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.shared.utlils.OptionListAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.Survey;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    @Nullable
    private CardsAdaptor cardsAdaptor;
    @Nullable
    private OptionListAdapter optionsAdapter;


    @Nullable
    private MainContract.View view;

    @Nullable
    private MainUseCase mainUseCase;

    public MainPresenter(@Nullable MainContract.View view) {
        this.view = view;
        this.mainUseCase = new MainInteractor();
    }


    /** List of Cards Start */
    @Override
    public RecyclerView cardViewInit(Context mContext, MainContract.View view, CardsAdaptor cardsAdaptor){

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10, mContext), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardsAdaptor);

        return recyclerView;
    }

    @Override
    public CardsAdaptor initCardAdaptor(Context mContext, List<Survey> surveyList, ArrayList<String> optionsList) {
        cardsAdaptor = new CardsAdaptor(mContext, surveyList, optionsList);

        return cardsAdaptor;
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp, Context mContext) {
        Resources r = mContext.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    /** List of Cards Ends */

    /** List of Options Starts */

    @Override
    public RecyclerView optionsViewInit(Context mContext, MainContract.View view, OptionListAdapter optionListAdapter) {
        RecyclerView optionsView = (RecyclerView) view.findViewById(R.id.option_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
        optionsView.setLayoutManager(mLayoutManager);
        optionsView.setItemAnimator(new DefaultItemAnimator());
        optionsView.setAdapter(optionListAdapter);

        return optionsView;
    }

    @Override
    public OptionListAdapter initOptionListAdapter(ArrayList<String> optionList) {
        optionsAdapter = new OptionListAdapter(optionList);
        return optionsAdapter;
    }

    /** List of Options Ends*/

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
