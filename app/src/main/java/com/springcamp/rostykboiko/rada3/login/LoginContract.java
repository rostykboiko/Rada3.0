package com.springcamp.rostykboiko.rada3.login;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public interface LoginContract {

    interface View {

        void tryLogin(Intent signInIntent);

        GoogleApiClient getGoogleApiClient();

        GoogleSignInOptions getGoogleSignInOptions();

    }

    interface Presenter {
        void logIn();

        void onStart();
    }
}
