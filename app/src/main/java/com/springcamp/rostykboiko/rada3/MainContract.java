package com.springcamp.rostykboiko.rada3;

import android.support.annotation.IdRes;

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
