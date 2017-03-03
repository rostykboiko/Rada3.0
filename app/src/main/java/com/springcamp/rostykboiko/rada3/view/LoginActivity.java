package com.springcamp.rostykboiko.rada3.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.springcamp.rostykboiko.rada3.LoginContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.presenter.LoginPresenter;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final int REQUEST_READ_CONTACTS = 0;

    private View mProgressView;
    private View mLoginFormView;

    @Nullable
    LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mProgressView.isShown();
    }

    @Override
    public String getEmail() {
        return "Vasa";
    }

    @Override
    public String getPassword() {
        return "123456";
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
    }


}

