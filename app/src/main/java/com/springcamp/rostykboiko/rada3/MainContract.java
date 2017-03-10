package com.springcamp.rostykboiko.rada3;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import com.springcamp.rostykboiko.rada3.shared.utlils.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.shared.utlils.OptionListAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.Survey;
import java.util.ArrayList;
import java.util.List;

public interface MainContract {

    interface View {
        android.view.View findViewById(@IdRes int id);

        void showProgress();
    }

    interface Presenter {

        void onStart();

        void onStop();
    }
}
