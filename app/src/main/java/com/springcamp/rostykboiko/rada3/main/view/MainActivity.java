package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.springcamp.rostykboiko.rada3.intro.view.MainIntroActivity;
import com.springcamp.rostykboiko.rada3.main.presenter.MainPresenter;
import com.springcamp.rostykboiko.rada3.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.shared.utlils.OptionListAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.Survey;
import com.springcamp.rostykboiko.rada3.editor.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.login.view.LoginActivity;
import com.springcamp.rostykboiko.rada3.settings.view.SettingsActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private List<Survey> surveyList;
    private ArrayList<String> optionslist = new ArrayList<>();
    private CardsAdaptor cardsAdaptor;
    private OptionListAdapter optionsAdapter;

    @Nullable
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
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

        cardViewInit();
        initOptions();
    }

    /**
     * List of Cards Start
     */

    private void optionsListViewInit(){
        optionsAdapter = new OptionListAdapter(optionslist);
        RecyclerView optionslistView = (RecyclerView) findViewById(R.id.option_recycler_view);
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        optionslistView.setLayoutManager(mListManager);
        optionslistView.setItemAnimator(new DefaultItemAnimator());
        optionslistView.setAdapter(cardsAdaptor);
    }

    private void cardViewInit(){
        cardsAdaptor = new CardsAdaptor(this, surveyList, optionslist);
        RecyclerView cardRecyclerView = (RecyclerView)findViewById(R.id.card_recycler);
        RecyclerView.LayoutManager mCardManager = new GridLayoutManager(this, 2);
        cardRecyclerView.setLayoutManager(mCardManager);
        cardRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cardRecyclerView.setAdapter(cardsAdaptor);
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * List of Cards End
     */

    private void initOptions() {
        surveyList = new ArrayList<>();
        optionslist.add("option1");
        optionslist.add("option2");
        optionslist.add("option3");
        //optionsAdapter.notifyDataSetChanged();

        Survey survey = new Survey("True Romance", optionslist);
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
        if (id == R.id.action_delete){
            Intent intent = new Intent(MainActivity.this, MainIntroActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}