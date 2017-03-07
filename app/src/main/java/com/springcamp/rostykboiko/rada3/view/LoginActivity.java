package com.springcamp.rostykboiko.rada3.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.springcamp.rostykboiko.rada3.LoginContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.adapter.OptionListAdapter;
import com.springcamp.rostykboiko.rada3.data.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements
        LoginContract.View,
        GoogleApiClient.OnConnectionFailedListener {

    private View mProgressView;
    private ProgressDialog mProgressDialog;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    @Nullable
    private GoogleAccountAdapter googleAccountAdapter;

    @Nullable
    LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleAccountAdapter = new GoogleAccountAdapter();

        presenter = new LoginPresenter(this);

        mProgressView = findViewById(R.id.login_progress);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter != null) {
                    presenter.logIn();
                }
            }
        });

        updateUI(googleAccountAdapter);
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
    public void tryLogin(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
        updateUI(googleAccountAdapter);
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, getGoogleSignInOptions())
                .build();
    }

    @Override
    public GoogleSignInOptions getGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Override
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    public void onViewStart() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    /**
     * Google SignIn Start
     **/
    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });

        updateUI(googleAccountAdapter);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            googleAccountAdapter = new GoogleAccountAdapter(
                    acct.getId(),
                    acct.getDisplayName(),
                    acct.getEmail(),
                    acct.getId(),
                    acct.getPhotoUrl());
            Log.d(TAG, "User name: " + googleAccountAdapter.getUserName());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    /**
     * Google SignIn End
     **/

    private void updateUI(GoogleAccountAdapter googleAccountAdapter) {
        if (googleAccountAdapter.getPersonId() != null) {
            TextView profileNameView = (TextView) findViewById(R.id.profile_name_tv);
            TextView profileEmailView = (TextView) findViewById(R.id.profile_email_tv);
            profileNameView.setText(googleAccountAdapter.getUserName());
            profileEmailView.setText(googleAccountAdapter.getUserEmail());
            findViewById(R.id.logo_img_view).setVisibility(View.GONE);
            findViewById(R.id.profile_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.logo_img_view).setVisibility(View.VISIBLE);
            findViewById(R.id.profile_layout).setVisibility(View.GONE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}

