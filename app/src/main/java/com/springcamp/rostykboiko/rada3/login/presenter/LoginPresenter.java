package com.springcamp.rostykboiko.rada3.login.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.springcamp.rostykboiko.rada3.LoginContract;
import com.springcamp.rostykboiko.rada3.login.data.LoginInteractor;
import com.springcamp.rostykboiko.rada3.login.data.LoginUseCase;

public class LoginPresenter implements LoginContract.Presenter {

    @Nullable
    private LoginContract.View view;

    @Nullable
    private LoginUseCase loginUseCase;

    public LoginPresenter(@NonNull LoginContract.View view) {
        this.view = view;
        this.loginUseCase = new LoginInteractor();
    }

    @Override
    public void logIn() {
        if (view != null) {
            view.showProgress();
            view.tryLogin(Auth.GoogleSignInApi.getSignInIntent(view.getGoogleApiClient()));
        }
    }

    @Override
    public void onStop() {
        view = null;
    }

    @Override
    public void onStart() {

    }
}
