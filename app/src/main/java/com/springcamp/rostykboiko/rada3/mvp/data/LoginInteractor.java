package com.springcamp.rostykboiko.rada3.mvp.data;

import android.support.annotation.NonNull;

/**
 * Created by rostykboiko on 28.02.2017.
 */

public class LoginInteractor implements LoginUseCase {

    @Override
    public void login(@NonNull String email,
                      @NonNull String password,
                      @NonNull LoginCallback callback) {
        callback.success();

    }
}
