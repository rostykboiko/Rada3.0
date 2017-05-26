package com.springcamp.rostykboiko.rada3.settings.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

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

    @BindView(R.id.profile_info)
    LinearLayout profileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getUserDetails().get("icon");

        initProfileInfo();
    }

    private void initProfileInfo() {
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

        if (GoogleAccountAdapter.getUserEmail() != null) {
            profileInfo.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.backBtn)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.info)
    void durationPicker() {
        startActivity(new Intent(SettingsActivity.this, infoDialogAnctivity.class));
    }

}
