package com.springcamp.rostykboiko.rada3.luckyWheel.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.springcamp.rostykboiko.rada3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LuckyWheelActyvity extends AppCompatActivity {
    private OptionEditorAdapter optionsAdapter;
    private ArrayList<String> optionsList = new ArrayList<>();

    @BindView(R.id.option_recycler_view)
    RecyclerView optionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lucky_wheel);

        ButterKnife.bind(this);


        List<WheelItem> wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.LTGRAY, BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_name)));
        wheelItems.add(new WheelItem(Color.BLUE, BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_name)));
        wheelItems.add(new WheelItem(Color.BLACK, BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_name)));
        wheelItems.add(new WheelItem(Color.GRAY, BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_name)));
        wheelItems.add(new WheelItem(Color.RED, BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_name)));
        wheelItems.add(new WheelItem(Color.GREEN, BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_name)));


        LuckyWheel lw = (LuckyWheel) findViewById(R.id.lwv);
        lw.addWheelItems(wheelItems);

        lw.rotateWheelTo(1);

        initOptionsListView();

    }
    private void isOptionsListEmpty() {
        if (optionsList != null && optionsList.isEmpty()) {
            optionsList.add("");
            optionsAdapter.notifyDataSetChanged();
        }
    }

    private void initOptionsListView() {
        optionsAdapter = new OptionEditorAdapter(optionsList, new OptionEditorAdapter.OptionItemsCallback() {
            @Override
            public boolean onOneOption() {
                return false;
            }

            @Override
            public void onOptionDeleted(int position) {
                optionsList.remove(position);
                optionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onOptionChanged(@NonNull ArrayList<String> options) {
                optionsList.isEmpty();
            }
        });

        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        optionsListView.setLayoutManager(mListManager);
        optionsListView.setItemAnimator(new DefaultItemAnimator());
        optionsListView.setAdapter(optionsAdapter);
    }

    @OnClick(R.id.btn_add_option)
    void addOptionRow() {
        if (optionsAdapter.getItemCount() < 5) {

            optionsList.add("");

            optionsAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Максимально 5 варіантів відповіді",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void launchActivity(@NonNull AppCompatActivity activity) {
        Intent intent = new Intent(activity, LuckyWheelActyvity.class);

        activity.startActivity(intent);
    }
}
