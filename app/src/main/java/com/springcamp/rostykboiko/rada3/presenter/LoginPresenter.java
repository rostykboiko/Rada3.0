package com.springcamp.rostykboiko.rada3.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.springcamp.rostykboiko.rada3.LoginContract;
import com.springcamp.rostykboiko.rada3.data.LoginInteractor;
import com.springcamp.rostykboiko.rada3.data.LoginUseCase;

/**
 * Created by rostykboiko on 28.02.2017.
 */

public class LoginPresenter implements LoginContract.Presenter{

    @Nullable
    private LoginContract.View view;

    @Nullable
    private LoginUseCase loginUseCase;

    public LoginPresenter(@NonNull LoginContract.View view){
        this.view = view;
        this.loginUseCase = new LoginInteractor();
    }

    @Override
    public void logIn() {
        view.showProgress();
        loginUseCase.login(view.getEmail(), view.getPassword(), new LoginUseCase.LoginCallback() {
            @Override
            public void success() {

            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void onStop() {
        view = null;
    }
}
