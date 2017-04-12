package com.springcamp.rostykboiko.rada3.editor.view;

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
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.springcamp.rostykboiko.rada3.bottomSheet.view.BottomSheet;
import com.springcamp.rostykboiko.rada3.editor.EditorContract;
import com.springcamp.rostykboiko.rada3.main.view.MainActivity;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.editor.presenter.EditorPresenter;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.security.SecureRandom;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EditorActivity extends AppCompatActivity implements EditorContract.View {
    private static final String SURVEY_KEY = "SURVEY_KEY";
    private ArrayList<String> optionsList = new ArrayList<>();
    private ArrayList<String> participants = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private OptionEditorAdapter optionsAdapter;
    private SecureRandom random = new SecureRandom();

    @Nullable
    private User user;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.saveBtn)
    ImageView saveButton;
    @BindView(R.id.one_option_switch)
    Switch oneOptionSwitch;

    @Nullable
    EditorContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        presenter = new EditorPresenter(this);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        userList = new ArrayList<>();

        getOptionsList();
        initClickListeners();
        initOptionsListView();
        addOptionRow();
        initParticipantsList();
    }

    /**
     * View init start
     */
    private void initClickListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveBtnPressed();
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
                startActivity(new Intent(EditorActivity.this, BottomSheet.class)
                        .putParcelableArrayListExtra("UserList", userList));
            }
        });

    }

    private void initOptionsListView() {
        optionsAdapter = new OptionEditorAdapter(optionsList);
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        optionsListView.setLayoutManager(mListManager);
        optionsListView.setItemAnimator(new DefaultItemAnimator());
        optionsListView.setAdapter(optionsAdapter);
    }

    private void initParticipantsList() {
        DatabaseReference mCurrentUserRef = FirebaseDatabase.getInstance().getReference()
                .child("User");
        mCurrentUserRef.keepSynced(true);

        mCurrentUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = new User();
                user.setUserID(dataSnapshot.child("UID").getValue(String.class));
                user.setUserName(dataSnapshot.child("Name").getValue(String.class));
                user.setUserEmail(dataSnapshot.child("Email").getValue(String.class));
                user.setAccountID(dataSnapshot.child("accountID").getValue(String.class));
                user.setDeviceToken(dataSnapshot.child("deviceToken").getValue(String.class));
                user.setUserProfileIcon(dataSnapshot.child("ProfileIconUrl").getValue(String.class));

                userList.add(user);
                System.out.println("User list " + userList);
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
            optionsAdapter.addNewItem();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Максимально 5 варіантів відповіді",
                    Toast.LENGTH_SHORT).show();
        }
        optionsAdapter.notifyDataSetChanged();

    }

    /* View init end */

    /**
     * Sync with Firebase Start
     */
    private void onSaveBtnPressed() {
        if (editTextTitle.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Дайте назву опитуванню",
                    Toast.LENGTH_SHORT).show();
        }
        if (optionsList.size() < 2) {
            Toast.makeText(getApplicationContext(),
                    "Додайте варіант відповіді",
                    Toast.LENGTH_SHORT).show();
        }
        if (participants.size() < 1) { // потрібно як мініум 2
            Toast.makeText(getApplicationContext(),
                    "Список кориситувачів пустий",
                    Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            dataSurveyReference(database);

            startActivity(new Intent(EditorActivity.this, MainActivity.class));
            finish();
        }
    }

    private void dataSurveyReference(FirebaseDatabase database) {
        String generatedString = generatedId();
        String surveyTitle = editTextTitle.getText().toString();

        DatabaseReference surveyRef = database.getReference("Survey");

        /* Title */
        surveyRef.child(generatedString)
                .child("Title")
                .setValue(editTextTitle.getText().toString());

        /* Options */
        for (String option : optionsList) {
            surveyRef.child(generatedString)
                    .child("Options")
                    .child("option" + (optionsList.indexOf(option) + 1))
                    .setValue(option);
            surveyRef.child(generatedString)
                    .child("Answers")
                    .child("option" + (optionsList.indexOf(option) + 1))
                    .child("0")
                    .setValue(0);
        }

        surveyRef.child(generatedString)
                .child("One Positive Option")
                .setValue(oneOptionSwitch.isChecked());

        /* Participants list */
        dataUserRef(database, generatedString, surveyTitle);
        System.out.println("userList editor" + userList);

    }

    private void dataUserRef(FirebaseDatabase database, String generatedString, String surveyTitle) {
        DatabaseReference userRef = database.getReference("User");
        DatabaseReference surveyRef = database.getReference("Survey");
        int userCounter = participants.size();

        if (GoogleAccountAdapter.getAccountID() != null)
            userRef
                    .child(GoogleAccountAdapter.getAccountID())
                    .child("Surveys")
                    .child(generatedString).setValue(userCounter);

        sendMessage(GoogleAccountAdapter.getDeviceToken(), generatedString, surveyTitle);

        for (String part : participants) {
            if (part != null) {
                for (User user : userList) {
                    if (user != null && user.getDeviceToken().equals(part)) {
                        userRef
                                .child(user.getAccountID())
                                .child("Surveys")
                                .child(generatedString).setValue(userCounter);

                        surveyRef
                                .child(generatedString)
                                .child("Participants")
                                .child(user.getAccountID()).setValue(part);

                        sendMessage(part, generatedString, surveyTitle);
                    }
                }
            }
        }

        surveyRef
                .child(generatedString)
                .child("Creator")
                .setValue(GoogleAccountAdapter.getAccountID());
    }

    public void sendMessage(String token, String surveyID, String surveyTitle) {
        System.out.println("Thread Token: " + token);

        Thread thread = new Thread(new OneShotTask(token, surveyID, surveyTitle));
        thread.start();
    }

    public String generatedId() {
        return new BigInteger(130, random).toString(32);
    }

    private class OneShotTask implements Runnable {
        String userToken;
        String mSurveyTitle;
        String generatedString;

        OneShotTask(String tokenThread, String surveyID, String surveyTitle) {
            userToken = tokenThread;
            mSurveyTitle = surveyTitle;
            generatedString = surveyID;
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

                System.out.println("Thread Token: " + userToken);
                notificationFCM.put("body", "Message sent from device");
                notificationFCM.put("title", "Survey");
                notificationFCM.put("sound", "default");
                notificationFCM.put("priority", "high");
                notificationFCM.put("surveyTitle", mSurveyTitle);
                notificationFCM.put("getSurveyId", generatedString);

                data.put("data", notificationFCM);
                data.put("to", userToken);
                OutputStream os = con.getOutputStream();
                os.write(data.toString().getBytes());
                os.close();
                System.out.println("Response Code: " + con.getResponseCode());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* Sync with Firebase Start */
    @Override
    protected void onResume() {
        super.onResume();
        participants.add(getIntent().getStringExtra("Participant"));
        System.out.println("Intent thread " + participants);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null)
            setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (editTextTitle.getText().toString().equals("") || optionsList.size() < 2) {
            startActivity(new Intent(EditorActivity.this, MainActivity.class));
            finish();
        } else {
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(EditorActivity.this);
            confirmDialog.setMessage("Дійсно скасувати"); // сообщение
            confirmDialog.setPositiveButton("Так", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(EditorActivity.this, MainActivity.class));
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
        intent.putExtra(SURVEY_KEY, survey);

        activity.startActivity(intent);
    }

}