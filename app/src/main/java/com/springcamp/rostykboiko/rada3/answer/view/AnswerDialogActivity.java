package com.springcamp.rostykboiko.rada3.answer.view;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.answer.AnswerContract;
import com.springcamp.rostykboiko.rada3.answer.presenter.AnswerDialogPresenter;
import com.springcamp.rostykboiko.rada3.main.view.RecyclerTouchListener;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerDialogActivity extends AppCompatActivity implements AnswerContract.View {
    private static final String SURVEY_KEY = "SURVEY_KEY";

    int position;
    private Survey survey = new Survey();
    private SessionManager session;
    private RecyclerView usersListView;
    private OptionDialogAdapter optionDialogAdapter;

    @BindView(R.id.title_survey_dialog)
    TextView titleView;

    @BindView(R.id.touch_outside)
    CoordinatorLayout outsideArea;

    @BindView(R.id.button_submit)
    Button okBtn;

    @Nullable
    AnswerContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_dialog);

        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());
        presenter = new AnswerDialogPresenter(this);

        initRecyclerView();
        initClickListeners();
        setStatusBarDim(true);
        messageReceiver();
    }

    private void messageReceiver() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String json = getIntent().getExtras().getString(SURVEY_KEY);
            survey = new Gson().fromJson(json, Survey.class);

            titleView.setText(survey.getSurveyTitle());
            optionDialogAdapter.setOptionsList(survey.getSurveyOptionList());

        }
    }

    private void initRecyclerView() {
        usersListView = (RecyclerView) findViewById(R.id.option_list_view);
        optionDialogAdapter = new OptionDialogAdapter(new OptionDialogAdapter.AnswerCheckCallback() {
            @Override
            public void onAnswerChecked(@NonNull Option option) {
                if (option.isChecked()) {
                    option.setChecked(true);
                } else {
                    option.setChecked(false);
                }
            }
        });
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        usersListView.setLayoutManager(mListManager);
        usersListView.setItemAnimator(new DefaultItemAnimator());
        usersListView.setAdapter(optionDialogAdapter);

    }

    @OnClick(R.id.button_submit)
    void okClick() {
        if (presenter != null) {
            presenter.submitAnswer();
        }
        finish();
    }

    private void initClickListeners() {
        usersListView.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(),
                usersListView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (optionDialogAdapter.getOptionsList().get(position).isChecked()) {
                            presenter.deleteCheckedItem(position, survey);
                        } else {
                            presenter.addCheckedItem(position, survey);
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );
    }


    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(dim ? Color.TRANSPARENT :
                    ContextCompat.getColor(this, getThemedResId(R.attr.colorPrimaryDark)));
        }
    }

    private int getThemedResId(@AttrRes int attr) {
        TypedArray typedArray = getTheme().obtainStyledAttributes(new int[]{attr});
        int resId = typedArray.getResourceId(0, 0);
        typedArray.recycle();
        return resId;
    }

    @Override
    public SessionManager getSession() {
        return session;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Survey getSurvey() {
        return survey;
    }

    public static void launchActivity(@NonNull AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, AnswerDialogActivity.class));

    }
}
