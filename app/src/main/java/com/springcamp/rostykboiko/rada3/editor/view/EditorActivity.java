package com.springcamp.rostykboiko.rada3.editor.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.EditorContract;
import com.springcamp.rostykboiko.rada3.editor.presenter.OptionEditorAdapter;
import com.springcamp.rostykboiko.rada3.main.view.MainActivity;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.editor.presenter.EditorPresenter;
import com.springcamp.rostykboiko.rada3.settings.view.SettingsActivity;
import com.springcamp.rostykboiko.rada3.shared.utlils.ItemListDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.widget.Toast;

public class EditorActivity extends AppCompatActivity implements EditorContract.View {

    private ArrayList<String> optionsList = new ArrayList<>();
    private OptionEditorAdapter optionsAdapter;

    @BindView(R.id.option_recycler_view)
    RecyclerView optionslistView;
    @BindView(R.id.rv_add_option)
    RelativeLayout addNewOption;
    @BindView(R.id.backBtn)
    ImageView backButton;
    @BindView(R.id.participants_row)
    RelativeLayout participantsBtn;
    @BindView(R.id.duration_row)
    RelativeLayout durationBtn;
    @BindView(R.id.tv_duration_time)
    TextView durationTime;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    EditorContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);
        presenter = new EditorPresenter(this);
        setSupportActionBar(toolbar);

        initClickListeners();
        initOptionsListView();
        addOptionRow();
    }

    private void initClickListeners() {
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

            }
        });
    }

    private void initOptionsListView() {
        optionslistView = (RecyclerView) findViewById(R.id.option_recycler_view);
        // TO DO: Without findViewById() butterKnife return null on View

        optionsAdapter = new OptionEditorAdapter(optionsList);
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        optionslistView.setLayoutManager(mListManager);
        optionslistView.setItemAnimator(new DefaultItemAnimator());
        optionslistView.setAdapter(optionsAdapter);
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
                Toast.makeText(getApplicationContext(),
                        "Тривалість: " + mDurationOptions[item],
                        Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    private void addOptionRow() {
        optionsList.add(getText(R.string.ed_option).toString());

        optionsAdapter.notifyDataSetChanged();
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(EditorActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_delete) {
            return true;
        }
        if (id == R.id.action_share) {
            return true;
        }
        if (id == R.id.backBtn) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
