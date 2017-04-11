package com.springcamp.rostykboiko.rada3.answer.data;

import android.support.annotation.Nullable;

import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;

public interface AnswerDialogUseCase {

    void submitAnswer(
            @Nullable SessionManager session,
            @Nullable String surveyId,
            @Nullable ArrayList<Option> optionsList,
            @Nullable AnswerDialogUseCase.AnswerCallBack callback);

    void addCheckedItem(@Nullable int position,
                        @Nullable ArrayList<Option> optionsList,
                        @Nullable ArrayList<Option> optionsAdapterList,
                        @Nullable AnswerCallBack callback);

    void deleteCheckedItem(@Nullable int position,
                           @Nullable ArrayList<Option> optionsList,
                           @Nullable ArrayList<Option> optionsAdapterList,
                           @Nullable AnswerCallBack callback);

    interface AnswerCallBack{
        void success();

        void error();
    }
}
