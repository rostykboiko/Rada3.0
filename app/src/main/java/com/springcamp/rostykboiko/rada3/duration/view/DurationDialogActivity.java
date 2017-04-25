package com.springcamp.rostykboiko.rada3.duration.view;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.springcamp.rostykboiko.rada3.R;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DurationDialogActivity extends AppCompatActivity {

    private Toast toast;

    ArrayList<String> hours;
    ArrayList<String> minutes;
    ArrayList<String> seconds;

    @BindView(R.id.loopViewHours)
    LoopView loopViewHours;

    @BindView(R.id.loopViewMin)
    LoopView loopViewMin;

    @BindView(R.id.loopViewSec)
    LoopView loopViewSec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_duration);

        ButterKnife.bind(this);


        setStatusBarDim(true);
        initLoops();
        setListeners();
    }


    private void initLoops(){
        hours = new ArrayList<>();
        for (int hour = 0; hour < 100; ++hour) {
            hours.add("" + hour);
        }

        minutes = new ArrayList<>();
        for (int min = 0; min < 60; min++) {
            minutes.add("" + min);
        }

        seconds = new ArrayList<>();
        for (int sec = 0; sec < 60; sec++) {
            seconds.add("" + sec);
        }

    }

    private void setListeners(){
        loopViewHours.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (toast == null) {
                    toast = Toast.makeText(DurationDialogActivity.this, "item " + index, Toast.LENGTH_SHORT);
                }
                toast.setText("item " + index);
                toast.show();
            }
        });

        loopViewMin.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (toast == null) {
                    toast = Toast.makeText(DurationDialogActivity.this, "item " + index, Toast.LENGTH_SHORT);
                }
                toast.setText("item " + index);
                toast.show();
            }
        });

        loopViewSec.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (toast == null) {
                    toast = Toast.makeText(DurationDialogActivity.this, "item " + index, Toast.LENGTH_SHORT);
                }
                toast.setText("item " + index);
                toast.show();
            }
        });

        loopViewHours.setCenterTextColor(getColor(R.color.colorAccentSecond));
        loopViewMin.setCenterTextColor(getColor(R.color.colorAccentSecond));
        loopViewSec.setCenterTextColor(getColor(R.color.colorAccentSecond));

        loopViewHours.setItems(hours);
        loopViewMin.setItems(minutes);
        loopViewSec.setItems(seconds);

        loopViewHours.setCurrentPosition(1);
        loopViewMin.setCurrentPosition(1);
        loopViewSec.setCurrentPosition(1);
    }

    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(dim ? Color.TRANSPARENT :
                    ContextCompat.getColor(this, getThemedResId(R.attr.colorPrimaryDark)));
        }
    }

    private int getThemedResId(@AttrRes int attr) {
        TypedArray typedArray = getTheme().obtainStyledAttributes(new int[]{attr});
        int resId = typedArray.getResourceId(0, 0);
        typedArray.recycle();
        return resId;
    }


}