package com.springcamp.rostykboiko.rada3.login.view;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.springcamp.rostykboiko.rada3.login.LoginContract;
import com.springcamp.rostykboiko.rada3.main.view.MainActivity;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.login.presenter.LoginPresenter;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements
        LoginContract.View,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private SessionManager session;
    private AuthCredential credential;
    private FirebaseAuth mAuth;

    @Nullable
    LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarDim(true);

        presenter = new LoginPresenter(this);
        session = new SessionManager(getApplicationContext());

        if (presenter != null) {
            presenter.logIn();
            initFireBaseListener();
        }
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

    private void initFireBaseListener() {
        mAuth = FirebaseAuth.getInstance();
        new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void tryLogin(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                handleSignInResult(result);
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        GoogleSignInAccount acct = result.getSignInAccount();
        if (acct != null && acct.getId() != null) {
            credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential);
            getUserData(acct);
        }
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();

    }

    private void getUserData(GoogleSignInAccount acct) {
        GoogleAccountAdapter.setAccountID(acct.getId());
        GoogleAccountAdapter.setUserName(acct.getDisplayName());
        GoogleAccountAdapter.setUserEmail(acct.getEmail());
        GoogleAccountAdapter.setProfileIcon(acct.getPhotoUrl().toString());
        GoogleAccountAdapter.setDeviceToken(FirebaseInstanceId.getInstance().getToken());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userList = database.getReference("User");

        userList.child(acct.getId()).child("Email").setValue(acct.getEmail());
        userList.child(acct.getId()).child("Name").setValue(acct.getDisplayName());
        userList.child(acct.getId()).child("accountID").setValue(acct.getId());
        userList.child(acct.getId()).child("ProfileIconUrl").setValue(acct.getPhotoUrl().toString());
        userList.child(acct.getId()).child("deviceToken")
                .setValue(FirebaseInstanceId.getInstance().getToken());

        System.out.println("mAuth.getCurrentUser()" + mAuth.getCurrentUser());

        session.createLoginSession(
                acct.getDisplayName(),
                acct.getEmail(),
                acct.getId(),
                FirebaseInstanceId.getInstance().getToken(),
                acct.getPhotoUrl().toString());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    /**
     * Google SignIn End
     **/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}

