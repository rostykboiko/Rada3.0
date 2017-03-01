package com.springcamp.rostykboiko.rada3;

/**
 * Created by rostykboiko on 28.02.2017.
 */

public interface LoginContract {

    interface View {
        String getEmail();

        String getPassword();

        void loginSuccess();

        void showProgress();
    }

    interface Presenter {
        void logIn();

        void onStop();
    }
}
