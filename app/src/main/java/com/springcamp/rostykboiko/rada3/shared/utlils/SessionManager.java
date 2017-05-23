package com.springcamp.rostykboiko.rada3.shared.utlils;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.springcamp.rostykboiko.rada3.login.view.LoginActivity;

public class SessionManager {
    private SharedPreferences pref;
    private Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ACCOUNTID = "accountID";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ICON = "icon";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String name,
                                   String email,
                                   String accountID,
                                   String deviceToken,
                                   String userProfileIcon) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ACCOUNTID, accountID);
        editor.putString(KEY_TOKEN, deviceToken);
        editor.putString(KEY_ICON, userProfileIcon);

        editor.commit();
    }

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent login = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(login);
        }

    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ACCOUNTID, pref.getString(KEY_ACCOUNTID, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_ICON, pref.getString(KEY_ICON, null));

        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    private boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
