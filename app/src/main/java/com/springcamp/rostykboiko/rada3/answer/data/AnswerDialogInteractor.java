package com.springcamp.rostykboiko.rada3.answer.data;

import android.support.annotation.Nullable;

import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;

public class AnswerDialogInteractor implements AnswerDialogUseCase {

    @Override
    public void submitAnswer(@Nullable Survey survey,
                             @Nullable SessionManager session,
                             @Nullable AnswerCallBack callback) {
        callback.success();
    }

    @Override
    public void addCheckedItem(@Nullable int position,
                               @Nullable Survey survey,
                               @Nullable AnswerCallBack callback) {
        callback.success();

    }

    @Override
    public void deleteCheckedItem(@Nullable int position,
                                  @Nullable Survey survey,
                                  @Nullable AnswerCallBack callback) {
        callback.success();
    }
}
