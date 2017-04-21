package com.springcamp.rostykboiko.rada3.answer.data;

import android.support.annotation.Nullable;

import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;

public interface AnswerDialogUseCase {

    void submitAnswer(
            @Nullable Survey survey,
            @Nullable SessionManager session,
            @Nullable AnswerCallBack callback);

    void addCheckedItem(@Nullable int position,
                        @Nullable Survey survey,
                        @Nullable AnswerCallBack callback);

    void deleteCheckedItem(@Nullable int position,
                           @Nullable Survey survey,
                           @Nullable AnswerCallBack callback);

    interface AnswerCallBack{
        void success();

        void error();
    }
}
