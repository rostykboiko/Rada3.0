package com.springcamp.rostykboiko.rada3.Main.view;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.springcamp.rostykboiko.rada3.Main.presenter.MainPresenter;
import com.springcamp.rostykboiko.rada3.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.shared.utlils.Survey;
import com.springcamp.rostykboiko.rada3.Editor.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.Login.view.LoginActivity;
import com.springcamp.rostykboiko.rada3.Settings.view.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private List<Survey> surveyList;
    private CardsAdaptor cardsAdaptor;
    private RecyclerView cardRecyclerView;
    ArrayList<String> list = new ArrayList<>();

    @Nullable
    MainContract.Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        presenter = new MainPresenter(this);

        surveyList = new ArrayList<>();

        cardsAdaptor = new CardsAdaptor(this, surveyList, list);

        cardRecyclerView = presenter.cardViewInit(this, this, cardsAdaptor);

        initOptions();
    }

    private void initOptions() {
        list.add("option1");
        list.add("option2");
        list.add("option3");
        Survey survey = new Survey("True Romance", list);
        surveyList.add(survey);

        survey = new Survey("True Romance");
        surveyList.add(survey);

        survey = new Survey("True Romance");
        surveyList.add(survey);

        survey = new Survey("True Romance");
        surveyList.add(survey);

        cardsAdaptor.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_share) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
