package com.springcamp.rostykboiko.rada3;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.springcamp.rostykboiko.rada3.shared.utlils.OptionListAdapter;

import java.util.ArrayList;

public interface EditorContract {

    interface View {
        String getId();

        String getSurveyTitle();

        android.view.View findViewById(@IdRes int id);

        ArrayList<String> getOptionsList();

        void showProgress();
    }

    interface Presenter {

        void getSurvey();

        void onStart();

        void onStop();
    }
}
