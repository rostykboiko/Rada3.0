package com.springcamp.rostykboiko.rada3.editor.view;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springcamp.rostykboiko.rada3.bottomSheet.view.BottomSheet;
import com.springcamp.rostykboiko.rada3.editor.EditorContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.editor.presenter.EditorPresenter;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.security.SecureRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EditorActivity extends AppCompatActivity implements EditorContract.View {
    private static final String SURVEY_KEY = "SURVEY_KEY";
    private Survey survey = new Survey();
    private ArrayList<String> participants = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private OptionEditorAdapter optionsAdapter;
    private SecureRandom random = new SecureRandom();

    @Nullable
    private User user;

    @BindView(R.id.edTitle)
    EditText editTextTitle;

    @BindView(R.id.option_recycler_view)
    RecyclerView optionsListView;

    @BindView(R.id.rv_add_option)
    RelativeLayout addNewOption;

    @BindView(R.id.tv_duration_time)
    TextView durationTime;

    @BindView(R.id.duration_row)
    RelativeLayout durationBtn;

    @BindView(R.id.participants_row)
    RelativeLayout participantsBtn;

    @BindView(R.id.backBtn)
    ImageView backButton;

    @BindView(R.id.one_option_switch)
    Switch oneOptionSwitch;

    @OnClick(R.id.fab)
    void okClick() {
        onSaveBtnPressed();
    }

    @Nullable
    EditorContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        presenter = new EditorPresenter(this);

        ButterKnife.bind(this);

        onSurveyEdit();
        getOptionsList();
        initClickListeners();
        initOptionsListView();
        initUsersList();
        isOptionsListEmpty();
    }

    /**
     * View init start
     */

    private void onSurveyEdit() {
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().getString("surveyJson") != null) {
            String json = getIntent().getExtras().getString("surveyJson");
            Gson gson = new Gson();

            survey = gson.fromJson(json, Survey.class);
            editTextTitle.setText(survey.getSurveyTitle());

            participants = survey.getParticipantsList();
            System.out.println("dataUserRef: here your list: " + survey.getParticipantsList());

            oneOptionSwitch.setChecked(survey.isSurveySingleOption());

        } else {
            survey.setSurveyID(generatedId());
        }
    }

    private void isOptionsListEmpty() {
        if (survey != null && survey.getSurveyOptionList().isEmpty()) {
            Option option = new Option();
            option.setOptionTitle("");
            option.setOptionKey("option" + survey.getSurveyOptionList().size());
            survey.getSurveyOptionList().add(option);
            optionsAdapter.notifyDataSetChanged();
        }
    }

    private void initClickListeners() {
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                survey.setSurveyTitle(s.toString());
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addNewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOptionRow();
            }
        });
        durationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                durationPicker();
            }
        });
        participantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jsonUserList = new Gson().toJson(userList);
                String jsonSurvey = new Gson().toJson(survey);

                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(EditorActivity.this, R.anim.fade_in, R.anim.fade_out);

                startActivity(new Intent(EditorActivity.this, BottomSheet.class)
                        .putExtra("surveyJson", jsonSurvey)
                        .putExtra("UserList", jsonUserList), options.toBundle());
            }
        });
        oneOptionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                survey.setSurveySingleOption(isChecked);
                optionsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initOptionsListView() {
        optionsAdapter = new OptionEditorAdapter(survey.getSurveyOptionList(), new OptionEditorAdapter.OptionItemsCallback() {
            @Override
            public boolean onOneOption() {
                return survey.isSurveySingleOption();
            }

            @Override
            public void onOptionDeleted(int position) {
                survey.getSurveyOptionList().remove(position);
                optionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onOptionChanged(@NonNull ArrayList<Option> options) {
                survey.setSurveyOptionList(options);
            }
        });

        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        optionsListView.setLayoutManager(mListManager);
        optionsListView.setItemAnimator(new DefaultItemAnimator());
        optionsListView.setAdapter(optionsAdapter);
    }

    private void initUsersList() {
        final DatabaseReference mCurrentUserRef = FirebaseDatabase.getInstance().getReference()
                .child("User");
        mCurrentUserRef.keepSynced(true);

        mCurrentUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = new User();

                user.setUserName(dataSnapshot.child("Name").getValue(String.class));
                user.setUserEmail(dataSnapshot.child("Email").getValue(String.class));
                user.setAccountID(dataSnapshot.child("accountID").getValue(String.class));
                user.setDeviceToken(dataSnapshot.child("deviceToken").getValue(String.class));
                user.setUserProfileIcon(dataSnapshot.child("ProfileIconUrl").getValue(String.class));
                user.setUserSurveys(userSurveys(mCurrentUserRef, user.getAccountID()));

                userList.add(user);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<String> userSurveys(DatabaseReference databaseReference, String accountID) {
        final ArrayList<String> arrayList = new ArrayList<>();
        databaseReference.child(accountID).child("Surveys").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayList.add(dataSnapshot.getKey());
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

        return arrayList;
    }

    private void durationPicker() {
        final String[] mDurationOptions = {
                "2 хвилини",
                "10 хвилин",
                "30 хвилин",
                "1 година",
                "Налаштування"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditorActivity.this);

        builder.setItems(mDurationOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        durationTime.setText(R.string.tv_duration_2min);
                        break;
                    case 1:
                        durationTime.setText(R.string.tv_duration_10min);
                        break;
                    case 2:
                        durationTime.setText(R.string.tv_duration_30min);
                        break;
                    case 3:
                        durationTime.setText(R.string.tv_duration_1h);
                        break;
                    case 4:
                        durationTime.setText("custom");
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addOptionRow() {
        if (optionsAdapter.getItemCount() < 5) {
            Option option = new Option();
            option.setOptionTitle("");
            option.setOptionKey("option" + (survey.getSurveyOptionList().size() + 1));
            survey.getSurveyOptionList().add(option);
            optionsAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Максимально 5 варіантів відповіді",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /* View init end */

    /**
     * Sync with Firebase Start
     */
    private void onSaveBtnPressed() {
        int actualSize = survey.getSurveyOptionList().size();
        for (Option option : survey.getSurveyOptionList()) {
            if (option.getOptionTitle().equals("")) {
                actualSize -= 1;
            }
        }
        if (editTextTitle.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Дайте назву опитуванню",
                    Toast.LENGTH_SHORT).show();
        } else if (actualSize < 2) {
            Toast.makeText(getApplicationContext(),
                    "Додайте варіант відповіді",
                    Toast.LENGTH_SHORT).show();
        } else if (participants != null && participants.size() < 2) { // потрібно як мініум 2
            Toast.makeText(getApplicationContext(),
                    "Список кориситувачів пустий",
                    Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            dataSurveyReference(database);

            finish();
        }
    }

    private void dataSurveyReference(FirebaseDatabase database) {
        DatabaseReference surveyRef = database.getReference("Survey");

        if (surveyRef.child(survey.getSurveyID()).getKey() != null)
            surveyRef.child(survey.getSurveyID()).removeValue();

        String surveyTitle = editTextTitle.getText().toString();

        System.out.println("surveyId editor " + survey.getSurveyID());

        /* Title */
        surveyRef.child(survey.getSurveyID())
                .child("Title")
                .setValue(surveyTitle);

        /* Options */
        for (Option option : survey.getSurveyOptionList()) {
            if (!option.getOptionTitle().equals("")) {
                surveyRef.child(survey.getSurveyID())
                        .child("Options")
                        .child("option" + (survey.getSurveyOptionList().indexOf(option) + 1))
                        .setValue(option.getOptionTitle());
                surveyRef.child(survey.getSurveyID())
                        .child("Answers")
                        .child("option" + (survey.getSurveyOptionList().indexOf(option) + 1))
                        .setValue(0);
            }
        }

        surveyRef.child(survey.getSurveyID())
                .child("One Positive Option")
                .setValue(oneOptionSwitch.isChecked());

        survey.setSurveyID(survey.getSurveyID());
        survey.setSurveyTitle(surveyTitle);
        survey.setSurveyOptionList(survey.getSurveyOptionList());
        survey.setCreatorId(GoogleAccountAdapter.getAccountID());
        survey.setSurveySingleOption(oneOptionSwitch.isChecked());
        survey.setParticipantsList(participants);
        survey.setParticipantsCount(participants.size());
        System.out.println("SingleOption editor " + survey.isSurveySingleOption());

        dataUserRef(database, survey);

    }

    private void dataUserRef(FirebaseDatabase database, Survey survey) {
        DatabaseReference userRef = database.getReference("User");
        DatabaseReference surveyRef = database.getReference("Survey");

        if (GoogleAccountAdapter.getAccountID() != null)
            userRef
                    .child(GoogleAccountAdapter.getAccountID())
                    .child("Surveys")
                    .child(survey.getSurveyID()).setValue(GoogleAccountAdapter.getAccountID());

        sendMessage(survey, GoogleAccountAdapter.getDeviceToken());

        for (String participant : survey.getParticipantsList()) {
            if (participant != null) {
                for (User user : userList) {
                    if (user != null && user.getAccountID().contains(participant)) {
                        System.out.println("dataUserRef: here your token: " + user.getDeviceToken());

                        surveyRef
                                .child(survey.getSurveyID())
                                .child("Participants")
                                .child(participant)
                                .setValue(user.getDeviceToken());

                        user.getUserSurveys().add(survey.getSurveyID());

                        userRef.child(user.getAccountID())
                                .child("Surveys")
                                .child(survey.getSurveyID())
                                .setValue(survey.getCreatorId());

                        sendMessage(survey, user.getDeviceToken());
                    }
                }
            }
        }

        surveyRef
                .child(survey.getSurveyID())
                .child("Creator")
                .setValue(GoogleAccountAdapter.getAccountID());
    }

    public void sendMessage(Survey survey, String token) {
        Thread thread = new Thread(new OneShotTask(survey, token));
        thread.start();
    }

    public String generatedId() {
        return new BigInteger(31, random).toString();
    }

    private class OneShotTask implements Runnable {
        Survey survey;
        String userToken;

        OneShotTask(Survey survey, String token) {
            this.survey = survey;
            this.userToken = token;
        }

        public void run() {
            try {
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);

                // HTTP request header
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Authorization", "key=" + getString(R.string.server_key));
                con.setRequestMethod("POST");
                con.connect();

                // HTTP request
                JSONObject data = new JSONObject();
                JSONObject notificationFCM = new JSONObject();

                String jsonSurvey = new Gson().toJson(survey);

                notificationFCM.put("body", "Message sent from device");
                notificationFCM.put("title", "Survey");
                notificationFCM.put("sound", "default");
                notificationFCM.put("priority", "high");
                notificationFCM.put("getSurveyId", survey.getSurveyID());
                notificationFCM.put("surveyTitle", survey.getSurveyTitle());
                notificationFCM.put(SURVEY_KEY, jsonSurvey);

                data.put("data", notificationFCM);
                data.put("to", userToken);

                OutputStream os = con.getOutputStream();
                os.write(data.toString().getBytes());
                os.close();

                System.out.println("Response Code : "
                        + con.getResponseCode());

            } catch (IOException | JSONException e) {
                System.out.println("Push error: " + e.getMessage());

                e.printStackTrace();
            }
        }
    }

    /* Sync with Firebase Start */
    @Override
    protected void onResume() {
        super.onResume();

        onSurveyEdit();

        String jsonParticipantsList = getIntent().getStringExtra("ParticipantsList");
        if (jsonParticipantsList != null) {
            Type type = new TypeToken<ArrayList<User>>() {
            }.getType();
            participants = new Gson().fromJson(jsonParticipantsList, type);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null)
            setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (editTextTitle.getText().toString().equals("") || survey.getSurveyOptionList().size() < 2) {
            finish();
        } else {
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(
                    new ContextThemeWrapper(this, R.style.AlertDialogCustom));
            confirmDialog.setMessage("Дійсно скасувати");
            confirmDialog.setPositiveButton("Так", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    finish();
                }
            });
            confirmDialog.setNegativeButton("Ні, редагувати далі", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                }
            });
            confirmDialog.setCancelable(true);
            confirmDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                }
            });
            confirmDialog.show();
        }
    }

    /**
     * Presenter Methods
     */
    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getSurveyTitle() {
        return null;
    }

    @Override
    public ArrayList<String> getOptionsList() {
        return null;
    }

    public static void launchActivity(@NonNull AppCompatActivity activity, @NonNull Survey survey) {
        Intent intent = new Intent(activity, EditorActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(survey);

        intent.putExtra(SURVEY_KEY, json);

        activity.startActivity(intent);
    }

}