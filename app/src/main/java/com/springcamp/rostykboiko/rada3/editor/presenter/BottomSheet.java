package com.springcamp.rostykboiko.rada3.editor.presenter;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.editor.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.main.presenter.RecyclerTouchListener;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;

import java.util.ArrayList;

public class BottomSheet extends AppCompatActivity {
    private BottomSheetBehavior bottomSheetBehavior;

    @Nullable
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDim(true);
        setContentView(R.layout.bottom_sheet);

        bottomSheetBehavior = new BottomSheetBehavior();

        findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (userList == null)
            userList = new ArrayList<>();
        initBehavior();
        initUsersList();
        initRecyclerView();
    }


    private void initBehavior(){
        BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))
                .setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_HIDDEN:
                                finish();
                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                                setStatusBarDim(false);
                                break;
                            default:
                                setStatusBarDim(true);
                                break;
                        }
                    }
                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        // no op
                    }
                });
    }

    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(dim ? Color.TRANSPARENT :
                    ContextCompat.getColor(this, getThemedResId(R.attr.colorPrimaryDark)));
        }
    }

    private int getThemedResId(@AttrRes int attr) {
        TypedArray a = getTheme().obtainStyledAttributes(new int[]{attr});
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }

    private void initUsersList() {
        System.out.println("Method: usersList ");

        DatabaseReference mUserListRef = FirebaseDatabase.getInstance().getReference()
                .child("User");
        mUserListRef.keepSynced(true);

        mUserListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userEmail = dataSnapshot.child("Email").getValue(String.class);
                String userName = dataSnapshot.child("Name").getValue(String.class);
                String userID = dataSnapshot.getKey();
                String profileIcon = dataSnapshot.child("ProfileIconUrl").getValue(String.class);
                System.out.println("User Email " + userEmail);

                User user = new User();
                user.setUserEmail(userEmail);
                user.setUserName(userName);
                user.setUserID(userID);
               // user.setUserProfileIcon(profileIcon);
                userList.add(user);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        final RecyclerView usersListView = (RecyclerView) findViewById(R.id.users_recycler_view);

        ParticipantsSheetAdapter participantsAdapter = new ParticipantsSheetAdapter(userList);
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        usersListView.setLayoutManager(mListManager);
        usersListView.setItemAnimator(new DefaultItemAnimator());
        usersListView.setAdapter(participantsAdapter);

        usersListView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                usersListView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        System.out.println("User " + userList.get(position).getUserID());
                        startActivity(new Intent(BottomSheet.this, EditorActivity.class).putExtra(
                                "Participant", userList.get(position).getUserID()));
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));
    }
}