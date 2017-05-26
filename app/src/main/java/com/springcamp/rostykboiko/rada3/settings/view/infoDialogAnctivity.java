package com.springcamp.rostykboiko.rada3.settings.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.springcamp.rostykboiko.rada3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class infoDialogAnctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.close_button)
    void close(){
        onBackPressed();
        finish();
    }
}
