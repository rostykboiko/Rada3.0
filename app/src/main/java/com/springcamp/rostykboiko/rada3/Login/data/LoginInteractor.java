package com.springcamp.rostykboiko.rada3.Login.data;

import android.support.annotation.NonNull;

public class LoginInteractor implements LoginUseCase {

    @Override
    public void login(@NonNull String email,
                      @NonNull String password,
                      @NonNull LoginCallback callback) {
        callback.success();

    }
}
