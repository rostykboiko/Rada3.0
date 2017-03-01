package com.springcamp.rostykboiko.rada3;

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
