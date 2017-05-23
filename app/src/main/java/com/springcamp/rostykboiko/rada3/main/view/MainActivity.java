package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.springcamp.rostykboiko.rada3.Rada3;
import com.springcamp.rostykboiko.rada3.answer.view.AnswerDialogActivity;
import com.springcamp.rostykboiko.rada3.luckyWheel.view.LuckyWheelActivity;
import com.springcamp.rostykboiko.rada3.main.presenter.MainPresenter;
import com.springcamp.rostykboiko.rada3.main.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.receiver.QuestionReceiver;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.editor.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.login.view.LoginActivity;
import com.springcamp.rostykboiko.rada3.settings.view.SettingsActivity;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;
import com.springcamp.rostykboiko.rada3.shared.utlils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity
        implements MainContract.View {
    private static final String SURVEY_KEY = "SURVEY_KEY";
    private boolean active;
    private SessionManager session;
    private Option option = new Option();
    private Survey survey = new Survey();
    private ArrayList<Survey> surveyList = new ArrayList<>();
    private CardsAdaptor cardsAdaptor;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionMenu floatingActionMenu;

    public QuestionReceiver questionReceiver;

    @Nullable
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        active = false;

        presenter = new MainPresenter(this);
        session = new SessionManager(getApplicationContext());

        initUserData();
        initFireBase();
        initCardView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Rada3.activityResumed();

        if (!active) {
            System.out.println("System hi" + active);

            questionReceiver = new QuestionReceiver(new QuestionReceiver.QuestionReceivedCallback() {
                @Override
                public void onQuestionReceived(@NonNull Survey survey) {
                    if (presenter != null) {
                        presenter.receivedQuestion(survey);
                    }
                }
            });
            LocalBroadcastManager.getInstance(this).registerReceiver(questionReceiver,
                    new IntentFilter(QuestionReceiver.QUESTION_RECEIVED_FILTER));

            active = true;
        }
        System.out.println("hi, daemon");
        initFireBase();
        initCardView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
        Rada3.activityPaused();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(questionReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
        Rada3.activityPaused();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(questionReceiver);

    }

    private void initUserData() {
        HashMap<String, String> user = session.getUserDetails();

        GoogleAccountAdapter.setUserID(user.get(SessionManager.KEY_UID));
        GoogleAccountAdapter.setUserName(user.get(SessionManager.KEY_NAME));
        GoogleAccountAdapter.setUserEmail(user.get(SessionManager.KEY_EMAIL));
        GoogleAccountAdapter.setAccountID(user.get(SessionManager.KEY_ACCOUNTID));
        GoogleAccountAdapter.setDeviceToken(user.get(SessionManager.KEY_TOKEN));
        GoogleAccountAdapter.setProfileIcon(user.get(SessionManager.KEY_ICON));
    }

    /* List of Cards Start */
    private void keepAnswersSynced(String surveyId) {
        DatabaseReference mCurrentSurvey;

        mCurrentSurvey = FirebaseDatabase.getInstance().getReference("Survey/" + surveyId + "/Answers");

        mCurrentSurvey.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("AnswerSync " + dataSnapshot.getKey() + " " + dataSnapshot.getValue());
                cardsAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                cardsAdaptor.notifyDataSetChanged();
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
        mCurrentSurvey.keepSynced(true);
    }

    private void initFireBase() {
        DatabaseReference mCurrentUserRef;
        if (GoogleAccountAdapter.getUserID() != null) {
            mCurrentUserRef = FirebaseDatabase.getInstance().getReference()
                    .child("User").child(GoogleAccountAdapter.getAccountID()).child("Surveys");

            Query mQueryUser = mCurrentUserRef;
            mQueryUser.orderByValue().addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    initSurveyById(dataSnapshot.getKey());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    cardsAdaptor.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    cardsAdaptor.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void initSurveyById(final String surveyId) {
        final DatabaseReference mCurrentSurvey = FirebaseDatabase
                .getInstance()
                .getReference("Survey/");

        keepAnswersSynced(surveyId);
        mCurrentSurvey.keepSynced(true);
        mCurrentSurvey.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot != null && dataSnapshot.getKey().equals(surveyId)) {
                            surveyList = new ArrayList<>();
                            survey.setSurveyID(dataSnapshot.getKey());
                            String surveyTitle = dataSnapshot.child("Title").getValue(String.class);
                            survey.setSurveyTitle(surveyTitle);

                            for (DataSnapshot optionSnapshot : dataSnapshot.child("Options").getChildren()) {
                                option.setOptionTitle(optionSnapshot.getValue().toString());
                                option.setAnswerCounter(dataSnapshot
                                        .child("Answers")
                                        .child(optionSnapshot.getKey())
                                        .getChildrenCount());

                                survey.getSurveyOptionList().add(option);
                                option = new Option();
                            }

                            survey.setParticipantsCount(Utils.longToInt(dataSnapshot
                                    .child("Participants")
                                    .getChildrenCount()));

                            if (dataSnapshot.child("One Positive Option").getValue() != null) {
                                survey.setSurveySingleOption(dataSnapshot
                                        .child("One Positive Option")
                                        .getValue(Boolean.class));
                            }

                            survey.setCreatorId(dataSnapshot
                                    .child("Creator")
                                    .getValue(String.class));

                            for (DataSnapshot partSnapshot : dataSnapshot.child("Participants").getChildren()) {
                                survey.getParticipantsList().add(partSnapshot.getKey());
                            }

                            surveyList.add(survey);
                            cardsAdaptor.setSurveyList(surveyList);

                            survey = new Survey();
                        }
                        cardsAdaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        onChildAdded(dataSnapshot, s);
                        cardsAdaptor.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        cardsAdaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void initCardView() {
        cardsAdaptor = new CardsAdaptor(new CardsAdaptor.QuestionsCardCallback() {
            @Override
            public void onCardDeleted(@NonNull Survey survey) {
                final String surveyId = survey.getSurveyID();

                FirebaseDatabase.getInstance().getReference()
                        .child("Survey")
                        .child(surveyId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("Creator").getValue() != null
                                        && dataSnapshot.child("Creator")
                                        .getValue().equals(GoogleAccountAdapter.getAccountID())) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Survey")
                                            .child(surveyId)
                                            .removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                FirebaseDatabase.getInstance().getReference()
                        .child("User")
                        .child(GoogleAccountAdapter.getAccountID())
                        .child("Surveys")
                        .child(surveyId)
                        .removeValue();

            }

            @Override
            public void onEditClick(@NonNull Survey survey) {
                if (GoogleAccountAdapter.getAccountID().equals(survey.getCreatorId())) {
                    String json = new Gson().toJson(survey);

                    startActivity(new Intent(MainActivity.this, EditorActivity.class)
                            .putExtra("surveyJson", json));
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.survey_toast, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        RecyclerView cardRecyclerView = (RecyclerView) findViewById(R.id.card_recycler);

        RecyclerView.LayoutManager mCardManager = new LinearLayoutManager(getApplicationContext());
        cardRecyclerView.setLayoutManager(mCardManager);
        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cardRecyclerView.setAdapter(cardsAdaptor);
    }
    /* List of Cards End */

    @Override
    public void showProgress() {
    }

    @Override
    public void showEditor(@NonNull Survey survey) {
        EditorActivity.launchActivity(this, survey);
    }

    @Override
    public void showReceivedQuestion(@NonNull Survey survey) {
        String json = new Gson().toJson(survey);

        startActivity(new Intent(MainActivity.this, AnswerDialogActivity.class)
                .putExtra(SURVEY_KEY, json));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (GoogleAccountAdapter.getUserEmail() == null)
            menu.findItem(R.id.action_profile).setTitle(R.string.action_login);
        else
            menu.findItem(R.id.action_profile).setTitle(R.string.action_logout);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (floatingActionMenu.isOpened()) {
            floatingActionMenu.close(true);
        }
        return super.dispatchTouchEvent(event);
    }

    @OnClick(R.id.menu_item1)
    void luckyWheel() {
        LuckyWheelActivity.launchActivity(MainActivity.this);
    }

    @OnClick(R.id.menu_item2)
    void createNewSurvey() {
        survey = new Survey();
        EditorActivity.launchActivity(MainActivity.this, survey);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.action_profile:
                if (GoogleAccountAdapter.getUserEmail() == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    onResume();
                    item.setTitle(R.string.action_logout);
                } else {
                    session.logoutUser();
                    GoogleAccountAdapter.logOut();
                    surveyList.clear();
                    cardsAdaptor.notifyDataSetChanged();
                    item.setTitle(R.string.action_login);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}