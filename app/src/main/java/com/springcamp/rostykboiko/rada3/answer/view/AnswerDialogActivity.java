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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.answer.AnswerContract;
import com.springcamp.rostykboiko.rada3.answer.presenter.AnswerDialogPresenter;
import com.springcamp.rostykboiko.rada3.main.view.RecyclerTouchListener;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerDialogActivity extends AppCompatActivity implements AnswerContract.View {
    int position;
    private String surveyId;
    private Survey survey = new Survey();
    private Option option = new Option();
    private ArrayList<Option> optionsList = new ArrayList<>();
    private ArrayList<String> answersList = new ArrayList<>();
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
            String surveyString = intent.getExtras().getString("survey");
            try {
                Gson gson = new Gson();
                JSONObject surveyJson = new JSONObject(surveyString);
                String jsonArray = surveyJson.get("Options").toString();

                optionsList = gson.fromJson(jsonArray, new TypeToken<ArrayList<Option>>(){}.getType());

                surveyId = surveyJson.get("SurveyID").toString();
                titleView.setText(surveyJson.get("SurveyTitle").toString());

                survey.setSurveyOptionList(optionsList);
                optionDialogAdapter.notifyDataSetChanged();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initRecyclerView() {
        usersListView = (RecyclerView) findViewById(R.id.option_list_view);

        optionDialogAdapter = new OptionDialogAdapter(survey.getSurveyOptionList(), new OptionDialogAdapter.AnswerCheckCallback() {
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
        presenter.submitAnswer();
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
                            optionsList.remove(
                                    optionDialogAdapter.getOptionsList().get(position));
                            optionDialogAdapter.getOptionsList().get(position).setChecked(false);
                            // presenter.deleteCheckedItem();
                            System.out.println("optionDialog remove");
                        } else {
                            optionsList.add(
                                    optionDialogAdapter.getOptionsList().get(position));
                            optionDialogAdapter.getOptionsList().get(position).setChecked(true);
                            System.out.println("optionDialog add");
                            //presenter.addCheckedItem();
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
    public String getSurveyId() {
        return surveyId;
    }

    @Override
    public ArrayList<Option> getOptionsList() {
        return optionsList;
    }

    @Override
    public ArrayList<Option> getAdaptorOptionsList() {
        return optionDialogAdapter.getOptionsList();
    }
}
