package com.springcamp.rostykboiko.rada3;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public interface LoginContract {

    interface View {
        String getEmail();

        String getPassword();

        void loginSuccess();

        void tryLogin(Intent signInIntent);

        GoogleApiClient getGoogleApiClient();

        GoogleSignInOptions getGoogleSignInOptions();

        void showProgress();

        void onViewStart();
    }

    interface Presenter {
        void logIn();

        void onStop();

        void onStart();
    }
}
