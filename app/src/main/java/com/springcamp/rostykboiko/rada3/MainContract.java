package com.springcamp.rostykboiko.rada3;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.springcamp.rostykboiko.rada3.shared.utlils.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.shared.utlils.OptionListAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.Survey;

import java.util.ArrayList;
import java.util.List;

public interface MainContract {

    interface View {
        String getId();

        String getSurveyTitle();

        android.view.View findViewById(@IdRes int id);

        ArrayList<String> getOptionsList();

        void showProgress();
    }

    interface Presenter {
        RecyclerView cardViewInit(Context mContext, MainContract.View view);

        CardsAdaptor initCardAdaptor(Context mContext, List<Survey> surveyList, ArrayList<String> optionsList);

        RecyclerView optionsViewInit(Context mContext, View view);

        OptionListAdapter initOptionListAdapter(ArrayList<String> optionList);

        void getSurvey();

        void onStart();

        void onStop();
    }
}
