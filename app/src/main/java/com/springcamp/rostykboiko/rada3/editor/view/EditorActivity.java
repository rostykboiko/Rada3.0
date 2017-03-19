package com.springcamp.rostykboiko.rada3.editor.view;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.springcamp.rostykboiko.rada3.EditorContract;
import com.springcamp.rostykboiko.rada3.editor.presenter.OptionEditorAdapter;
import com.springcamp.rostykboiko.rada3.main.view.MainActivity;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.editor.presenter.EditorPresenter;
import com.springcamp.rostykboiko.rada3.shared.utlils.ItemListDialogFragment;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.widget.Toast;

public class EditorActivity extends AppCompatActivity implements EditorContract.View,
        ItemListDialogFragment.Listener {
    private String[] separated;
    private List<String> optionsList = new ArrayList<>();
    private List<String> userList = new ArrayList<>();
    private OptionEditorAdapter optionsAdapter;
    private SecureRandom random = new SecureRandom();


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

        ButterKnife.bind(this);
        presenter = new EditorPresenter(this);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initClickListeners();
        initOptionsListView();
        addOptionRow();
    }

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
                ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
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
                separated = mDurationOptions[item].split(" ");
                if (separated[1].equals("година"))
                    durationTime.setText(separated[0] + getString(R.string.tv_duration_hour));
                else
                    durationTime.setText(separated[0] + getString(R.string.tv_duration_min));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addOptionRow() {
        if (optionsList.size() < 5) {
            optionsList.add(getText(R.string.ed_option).toString());
            optionsAdapter.notifyDataSetChanged();
        } else {
            addNewOption.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                    "Максимально 5 варіантів відповіді",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void addUser() {

    }

    private void onSaveBtnPressed() {
        if (editTextTitle.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Дайте назву опитуванню",
                    Toast.LENGTH_SHORT).show();
        } else if (optionsList.size() < 2) {
            Toast.makeText(getApplicationContext(),
                    "Додайте варіант відповіді",
                    Toast.LENGTH_SHORT).show();
        } else {
            String generatedString = generatedId();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference surveyRef = database.getReference("Survey");
            surveyRef.child(generatedString)
                    .child("Title")
                    .setValue(editTextTitle.getText().toString());
            for (String option : optionsList) {
                surveyRef.child(generatedString)
                        .child("Options")
                        .child("option" + (optionsList.indexOf(option) + 1))
                        .setValue(option);
            }

            surveyRef.child(generatedString)
                    .child("One Positive Option")
                    .setValue(oneOptionSwitch.isChecked());
            if (separated != null)
                surveyRef.child(generatedString)
                        .child("duration")
                        .setValue(separated[0] + " " + separated[1]);
            else surveyRef.child(generatedString)
                    .child("duration")
                    .setValue(getString(R.string.tv_duration_2min));

            surveyRef.child(generatedString)
                    .child("color")
                    .setValue("#color");

            for (String user : userList) {
                surveyRef.child(generatedString)
                        .child("Participants")
                        .child("user" + (optionsList.indexOf(user) + 1))
                        .setValue(user);
            }

            startActivity(new Intent(EditorActivity.this, MainActivity.class));
            finish();
        }
    }

    public String generatedId() {
        return new BigInteger(130, random).toString(32);
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

    // Presenter
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

    // Bottom Sheet
    @Override
    public void onItemClicked(int position) {

    }
}
