package com.springcamp.rostykboiko.rada3.editor.view;

import android.content.Intent;
import android.support.annotation.Nullable;
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

import com.springcamp.rostykboiko.rada3.EditorContract;
import com.springcamp.rostykboiko.rada3.editor.presenter.OptionEditorAdapter;
import com.springcamp.rostykboiko.rada3.main.view.MainActivity;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.main.presenter.OptionCardAdapter;
import com.springcamp.rostykboiko.rada3.editor.presenter.EditorPresenter;
import com.springcamp.rostykboiko.rada3.settings.view.SettingsActivity;
import java.util.ArrayList;

import butterknife.BindView;

public class EditorActivity extends AppCompatActivity implements EditorContract.View {

    private ArrayList<String> optionsList = new ArrayList<>();
    private OptionEditorAdapter optionsAdapter;

    @BindView(R.id.option_recycler_view) RecyclerView optionslistView;
    @BindView(R.id.rv_add_option)RelativeLayout addNewOption;
    @BindView(R.id.backBtn)ImageView backButton;

    @Nullable
    EditorContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        presenter = new EditorPresenter(this);

        initViewItems();
        initClickListeners();
        initOptionsListView();
        addOptionRow();
    }

    private void initViewItems(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backButton = (ImageView) findViewById(R.id.backBtn);
        addNewOption = (RelativeLayout) findViewById(R.id.rv_add_option);
    }

    private void initClickListeners(){
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
    }

    private void initOptionsListView(){
        optionslistView = (RecyclerView) findViewById(R.id.option_recycler_view);
        // TO DO: Without findViewById() butterKnife return null on View

        optionsAdapter = new OptionEditorAdapter(optionsList);
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        optionslistView.setLayoutManager(mListManager);
        optionslistView.setItemAnimator(new DefaultItemAnimator());
        optionslistView.setAdapter(optionsAdapter);
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
