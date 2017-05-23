package com.springcamp.rostykboiko.rada3.luckyWheel.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.springcamp.rostykboiko.rada3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LuckyWheelActivity extends AppCompatActivity {
    private OptionsAdapter optionsAdapter;
    private boolean wheel = false;
    private ArrayList<String> optionsList = new ArrayList<>();
    private List<WheelItem> wheelItems = new ArrayList<>();
    private Random random = new Random();
    private LuckyWheel lw;
    private InputMethodManager inputMethodManager;

    private final int[] colorsList = {
            0xFFf44336,
            0xFF2196f3,
            0xFF4caf50,
            0xFFffc107,
            0xff009688,
            0xFF9b59b6
    };

    @BindView(R.id.chosenOption)
    TextView chosenOptionTV;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.option_recycler_view)
    RecyclerView optionsListView;

    @BindView(R.id.rv_container)
    RelativeLayout recycler_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lucky_wheel);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initWheelView();
        initOptionsListView();
        isOptionsListEmpty();
    }

    private void initWheelView() {
        lw = (LuckyWheel) findViewById(R.id.lwv);

        lw.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                chosenOptionTV.setVisibility(View.VISIBLE);
            }
        });
        lw.addWheelItems(wheelItems);
    }

    @OnClick(R.id.fab)
    void spinWheel() {
        if (!wheel) {
            lw = (LuckyWheel) findViewById(R.id.lwv);
            initWheelView();

            recycler_container.setVisibility(View.GONE);
            lw.setVisibility(View.VISIBLE);
            int chosenOption = random.nextInt(optionsList.size());
            lw.rotateWheelTo(chosenOption);

            if (!optionsList.get(chosenOption).equals("")) {
                chosenOptionTV.setText(getString(R.string.chosen_lucky_wheel) + optionsList.get(chosenOption));
            } else {
                chosenOptionTV.setText(getString(R.string.chosen_lucky_wheel) + (chosenOption + 1));
            }

            floatingActionButton.setImageResource(R.drawable.ic_material_refresh);

            wheel = true;
        } else {
            recycler_container.setVisibility(View.VISIBLE);
            floatingActionButton.setImageResource(R.drawable.ic_custom_lucky);

            chosenOptionTV.setVisibility(View.GONE);
            lw.setVisibility(View.GONE);
            lw = (LuckyWheel) findViewById(R.id.lwv);

            optionsList.clear();
            wheelItems.clear();

            isOptionsListEmpty();
            optionsAdapter.notifyDataSetChanged();

            wheel = false;
        }
    }

    private void isOptionsListEmpty() {
        if (optionsList != null && optionsList.isEmpty()) {
            optionsList.add("");
            wheelItems.add(new WheelItem(colorsList[0], BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_material_addgroup)));

            optionsAdapter.notifyDataSetChanged();
        }
    }

    private void initOptionsListView() {
        optionsAdapter = new OptionsAdapter(optionsList, new OptionsAdapter.OptionItemsCallback() {
            @Override
            public void onOptionDeleted(int position) {
                optionsList.remove(position);
                wheelItems.remove(position);
                optionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onOptionChanged(@NonNull ArrayList<String> options) {
                optionsList = options;
            }
        });

        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        optionsListView.setLayoutManager(mListManager);
        optionsListView.setItemAnimator(new DefaultItemAnimator());
        optionsListView.setAdapter(optionsAdapter);
    }

    @OnClick(R.id.rv_add_option)
    void addOptionRow() {
        int maxLength = 6;
        if (optionsAdapter.getItemCount() < maxLength) {

            optionsList.add("");
            wheelItems.add(new WheelItem(colorsList[optionsList.size() - 1], BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_material_account)));

            optionsAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Максимально " + maxLength + " варіантів відповіді",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void launchActivity(@NonNull AppCompatActivity activity) {
        Intent intent = new Intent(activity, LuckyWheelActivity.class);

        activity.startActivity(intent);
    }

    @OnClick(R.id.backBtn)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
