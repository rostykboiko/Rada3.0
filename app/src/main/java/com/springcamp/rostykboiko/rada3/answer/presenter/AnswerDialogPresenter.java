package com.springcamp.rostykboiko.rada3.answer.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.springcamp.rostykboiko.rada3.answer.AnswerContract;
import com.springcamp.rostykboiko.rada3.answer.data.AnswerDialogInteractor;
import com.springcamp.rostykboiko.rada3.answer.data.AnswerDialogUseCase;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

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
        answerDialogUseCase.submitAnswer(view.getSurvey(),
                view.getSession(),
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                HashMap<String, String> user = view.getSession().getUserDetails();

                Survey survey = view.getSurvey();

                DatabaseReference mCurrentSurvey = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Survey")
                        .child(survey.getSurveyID())
                        .child("Answers");
                int index = 1;
                for (Option option : survey.getSurveyOptionList()) {
                    if (option.isChecked()){
                    mCurrentSurvey
                            .child("option" + index)
                            .child(user.get(SessionManager.KEY_ACCOUNTID))
                            .setValue(user.get(SessionManager.KEY_ICON));
                    }
                    index++;
                }
            }

            @Override
            public void error() {

            }
        });

    }

    @Override
    public void addCheckedItem(final int position, @NonNull final Survey survey) {
        answerDialogUseCase.addCheckedItem(
                position,
                survey,
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                survey.getSurveyOptionList().get(position).setChecked(true);
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void deleteCheckedItem(final int position, @NonNull final Survey survey) {
        answerDialogUseCase.deleteCheckedItem(
                position,
                survey,
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                survey.getSurveyOptionList().get(position).setChecked(false);
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void radioChecked(@Nullable final int position, @Nullable final Survey survey) {
        answerDialogUseCase.radioChecked(
                position,
                survey,
                new AnswerDialogUseCase.AnswerCallBack(){
                    @Override
                    public void success() {
                        System.out.println("sloooowwww " + position);

                        for (Option option : view.getSurvey().getSurveyOptionList()){
                            option.setChecked(false);
                        }

                        survey.getSurveyOptionList().get(position).setChecked(true);
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
