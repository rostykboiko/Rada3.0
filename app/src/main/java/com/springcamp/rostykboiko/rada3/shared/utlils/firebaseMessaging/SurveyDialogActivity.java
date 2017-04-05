package com.springcamp.rostykboiko.rada3.shared.utlils.firebaseMessaging;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.main.presenter.RecyclerTouchListener;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurveyDialogActivity extends AppCompatActivity {
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
    @BindView(R.id.button_ok)
    Button okBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity_survey);

        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());

        initRecyclerView();
        initClickListeners();
        setStatusBarDim(true);
        messageReceiver();
    }

    private void messageReceiver() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            surveyId = intent.getExtras().getString("surveyID");
            initSurveyById(surveyId);
        }
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

    private void initSurveyById(final String surveyId) {
        DatabaseReference mCurrentSurvey = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Survey");

        mCurrentSurvey.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().equals(surveyId)) {
                            String surveyTitle = dataSnapshot.child("Title").getValue(String.class);
                            survey.setSurveyTitle(surveyTitle);
                            titleView.setText(surveyTitle);

                            for (DataSnapshot child : dataSnapshot.child("Options").getChildren()) {
                                option.setOptionTitle(child.getValue().toString());
                                option.setOptionKey(child.getKey());
                                survey.getSurveyOptionList().add(option);
                                option = new Option();
                            }

                            for (DataSnapshot child : dataSnapshot.child("Answers").getChildren()) {
                                answersList.add(child.getValue().toString());
                            }

                            System.out.println("Answers " + answersList);
                            optionDialogAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void initRecyclerView() {
        usersListView = (RecyclerView) findViewById(R.id.option_list_view);
        optionDialogAdapter = new OptionDialogAdapter(survey.getSurveyOptionList());
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        usersListView.setLayoutManager(mListManager);
        usersListView.setItemAnimator(new DefaultItemAnimator());
        usersListView.setAdapter(optionDialogAdapter);

    }

    private void initClickListeners() {
        outsideArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer();
                finish();
            }
        });

        usersListView.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(),
                usersListView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (optionsList.contains(
                                optionDialogAdapter.getOptionsList().get(position))) {
                            optionsList.remove(
                                    optionDialogAdapter.getOptionsList().get(position));
                        } else {
                            optionsList.add(optionDialogAdapter
                                    .getOptionsList()
                                    .get(position));
                        }

                        Toast.makeText(getApplicationContext(),
                                "Checked: " +
                                        optionDialogAdapter
                                                .getOptionsList()
                                                .get(position)
                                                .getOptionTitle(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );
    }

    private void submitAnswer() {
        HashMap<String, String> user = session.getUserDetails();

        //GoogleAccountAdapter.setAccountID(user.get(SessionManager.KEY_ACCOUNTID));

        DatabaseReference mCurrentSurvey = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Survey")
                .child(surveyId)
                .child("Answers");

        for (Option option : optionsList) {
            mCurrentSurvey.child(option.getOptionKey())
                    .child(user.get(SessionManager.KEY_ACCOUNTID))
                    .setValue(user.get(SessionManager.KEY_ACCOUNTID));
            System.out.println("Key " + option.getOptionKey() + " option " + option);
        }
    }
}
