package com.springcamp.rostykboiko.rada3.answer.presenter;

import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.springcamp.rostykboiko.rada3.answer.AnswerContract;
import com.springcamp.rostykboiko.rada3.answer.data.AnswerDialogInteractor;
import com.springcamp.rostykboiko.rada3.answer.data.AnswerDialogUseCase;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AnswerDialogPresenter implements AnswerContract.Presenter {

    @Nullable
    private AnswerContract.View view;
    @Nullable
    private AnswerDialogUseCase answerDialogUseCase;

    public AnswerDialogPresenter(@Nullable AnswerContract.View view){
        this.view = view;
        this.answerDialogUseCase = new AnswerDialogInteractor();
    }


    @Override
    public void submitAnswer() {
        answerDialogUseCase.submitAnswer(view.getSession(), view.getSurveyId(), view.getOptionsList(),
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                HashMap<String, String> user = view.getSession().getUserDetails();

                DatabaseReference mCurrentSurvey = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Survey")
                        .child(view.getSurveyId())
                        .child("Answers");

                for (Option option : view.getOptionsList()) {
                    mCurrentSurvey.child(option.getOptionKey())
                            .child(user.get(SessionManager.KEY_ACCOUNTID))
                            .setValue(user.get(SessionManager.KEY_ACCOUNTID));
                    System.out.println("Key " + option.getOptionKey() + " option " + option);
                }
            }

            @Override
            public void error() {

            }
        });

    }

    @Override
    public void addCheckedItem() {
        answerDialogUseCase.addCheckedItem(view.getPosition(),
                view.getAdaptorOptionsList(),
                view.getOptionsList(),
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                view.getAdaptorOptionsList().get(view.getPosition()).setChecked(true);
                view.getOptionsList().add(view.getAdaptorOptionsList().get(view.getPosition()));
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void deleteCheckedItem() {
        answerDialogUseCase.addCheckedItem(view.getPosition(),
                view.getOptionsList(),
                view.getOptionsList(),
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                view.getAdaptorOptionsList().get(view.getPosition()).setChecked(false);
                view.getOptionsList().remove(view.getAdaptorOptionsList().get(view.getPosition()));
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        view = null;
    }

}
