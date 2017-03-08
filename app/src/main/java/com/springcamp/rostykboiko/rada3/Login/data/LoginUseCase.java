package com.springcamp.rostykboiko.rada3.Login.data;

import android.support.annotation.NonNull;

public interface LoginUseCase {

    void login(@NonNull String email,
               @NonNull String password,
               @NonNull LoginCallback callback);

    interface LoginCallback{
        void success();

        void error();
    }
}