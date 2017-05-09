package com.springcamp.rostykboiko.rada3.settings.view;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.Rada3;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;
import com.springcamp.rostykboiko.rada3.shared.utlils.Utils;

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

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getUserDetails().get("icon");

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

    @OnClick(R.id.theme)
    void durationPicker() {
        final String[] mDurationOptions = {
                getString(R.string.theme_light),
                getString(R.string.theme_yellow),
                getString(R.string.theme_dark)};

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(getString(R.string.theme_dark));
        builder.setItems(mDurationOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Utils.changeToTheme(SettingsActivity.this, Utils.THEME_DEFAULT);
                        break;
                    case 1:
                        Utils.changeToTheme(SettingsActivity.this, Utils.THEME_YELLOW);
                        break;
                    case 2:

                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
