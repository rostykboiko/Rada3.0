package com.springcamp.rostykboiko.rada3.durationPicker.view;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.editor.view.EditorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DurationDialogActivity extends AppCompatActivity {
    private int clickCount = 1;

    @BindView(R.id.minutes)
    TextView minutes;
    @BindView(R.id.seconds)
    TextView seconds;

    @BindView(R.id.button_1)
    TextView button1;
    @BindView(R.id.button_2)
    TextView button2;
    @BindView(R.id.button_3)
    TextView button3;
    @BindView(R.id.button_4)
    TextView button4;
    @BindView(R.id.button_5)
    TextView button5;
    @BindView(R.id.button_6)
    TextView button6;
    @BindView(R.id.button_7)
    TextView button7;
    @BindView(R.id.button_8)
    TextView button8;
    @BindView(R.id.button_9)
    TextView button9;
    @BindView(R.id.button_0)
    TextView button0;
    @BindView(R.id.button_cancel)
    TextView buttonCancel;
    @BindView(R.id.button_save)
    TextView buttonSave;
    @BindView(R.id.deleteBtn)
    ImageView deleteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_duration);

        ButterKnife.bind(this);

        setStatusBarDim(true);
        initClickListeners();
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

    private void setTime(int buttonNumber) {
        int firstNumber;
        int secondNumber;
        int hoursInt;
        int minutesInt;
        int secondsInt;

        deleteButton.setImageDrawable(getDrawable(R.drawable.ic_material_backspace_white));

        if (clickCount == 1) {
            secondsInt = Integer.parseInt(seconds.getText().toString());
            firstNumber = secondsInt % 10;
            seconds.setText("" + firstNumber + buttonNumber);
        }
        if (clickCount == 2) {
            secondNumber = buttonNumber;
            secondsInt = Integer.parseInt(seconds.getText().toString());
            firstNumber = secondsInt % 10;
            seconds.setText("" + firstNumber + secondNumber);
        }
        if (clickCount == 3) {
            secondsInt = Integer.parseInt(seconds.getText().toString());
            firstNumber = secondsInt % 10;
            secondNumber = secondsInt / 10;
            minutes.setText("" + 0 + secondNumber);
            seconds.setText("" + firstNumber + buttonNumber);
        }
        if (clickCount == 4) {
            minutesInt = Integer.parseInt(minutes.getText().toString());
            secondsInt = Integer.parseInt(seconds.getText().toString());

            firstNumber = minutesInt % 10;
            secondNumber = secondsInt / 10;
            minutes.setText("" + firstNumber + secondNumber);
            firstNumber = secondsInt % 10;
            seconds.setText("" + firstNumber + buttonNumber);
        }

        if (clickCount < 5) {
            clickCount++;
        }
    }

    private void deleteTime() {
        int firstNumber;
        int secondNumber;
        int minutesInt;
        int secondsInt;

        minutesInt = Integer.parseInt(minutes.getText().toString());
        secondsInt = Integer.parseInt(seconds.getText().toString());

        firstNumber = 0;
        secondNumber = minutesInt / 10;
        minutes.setText("" + firstNumber + secondNumber);
        firstNumber = minutesInt % 10;
        secondNumber = secondsInt / 10;
        seconds.setText("" + firstNumber + secondNumber);

        if (clickCount > 1) {
            clickCount--;
        }
        if (clickCount == 1) {
            deleteButton.setImageDrawable(getDrawable(R.drawable.ic_material_backspace_white03));
        }
    }

    void initClickListeners() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(5);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(6);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(7);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(8);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(9);
            }
        });
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(0);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DurationDialogActivity.this, EditorActivity.class)
                        .putExtra("minutes", minutes.getText().toString())
                        .putExtra("seconds", seconds.getText().toString())
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTime();
            }
        });
        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                seconds.setText(R.string.time_seconds_00);
                minutes.setText(R.string.time_minutes_00);
                return true;
            }
        });


    }
}