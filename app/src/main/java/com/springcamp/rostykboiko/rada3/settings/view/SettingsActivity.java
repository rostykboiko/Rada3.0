package com.springcamp.rostykboiko.rada3.settings.view;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.profile_img_view)
    ImageView profileIcon;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.userNameItem)
    TextView userNameTV;

    @BindView(R.id.userEmailItem)
    TextView userEmailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        toolbarTitle.setText(R.string.title_activity_settings);

        initProfileInfo();
    }

    private void initProfileInfo(){
        userNameTV.setText(GoogleAccountAdapter.getUserName());
        userEmailTV.setText(GoogleAccountAdapter.getUserEmail());

        Glide.with(profileIcon.getContext()).load(GoogleAccountAdapter.getProfileIcon())
                .asBitmap().into(new BitmapImageViewTarget(profileIcon) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(profileIcon.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                profileIcon.setImageDrawable(circularBitmapDrawable);
            }
        });

    }

    @OnClick(R.id.backBtn)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
