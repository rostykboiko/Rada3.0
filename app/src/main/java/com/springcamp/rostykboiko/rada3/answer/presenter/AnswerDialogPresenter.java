package com.springcamp.rostykboiko.rada3.answer.presenter;

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
                int index = 0;
                for (Option option : survey.getSurveyOptionList()) {
                    if (option.isChecked()){
                    mCurrentSurvey
                            .child("option" + (index + 1))
                            .child(user.get(SessionManager.KEY_ACCOUNTID))
                            .setValue(user.get(SessionManager.KEY_ACCOUNTID));
                    }
                    System.out.println("Key " + option.getOptionKey() + " option " + option);
                    index++;
                }
            }

            @Override
            public void error() {

            }
        });

    }

    @Override
    public void addCheckedItem() {
        answerDialogUseCase.addCheckedItem(
                view.getPosition(),
                view.getSurvey(),
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                view.getSurvey().getSurveyOptionList().get(view.getPosition()).setChecked(true);
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void deleteCheckedItem() {
        answerDialogUseCase.deleteCheckedItem(
                view.getPosition(),
                view.getSurvey(),
                new AnswerDialogUseCase.AnswerCallBack() {
            @Override
            public void success() {
                view.getSurvey().getSurveyOptionList().get(view.getPosition()).setChecked(false);

               // view.getCheckedOptionsList().get(view.getPosition()).setChecked(false);
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
