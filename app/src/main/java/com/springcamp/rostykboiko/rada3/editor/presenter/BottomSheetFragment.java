package com.springcamp.rostykboiko.rada3.editor.presenter;

import android.app.Dialog;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
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

import java.net.URL;
import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private View contentView;
    private ArrayList<User> userList = new ArrayList<>();

    private BottomSheetBehavior.BottomSheetCallback
            mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
            System.out.println("Method: onStateChanged ");

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            System.out.println("Method: onSlide ");
        }
    };

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        contentView = View.inflate(getContext(), R.layout.fragment_item_list_dialog, null);
        dialog.setContentView(contentView);
        System.out.println("Method: setupDialog ");

        initRecyclerView();
        initUsersList();
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
                String userID = dataSnapshot.child("Uid").getValue(String.class);
                String profileIcon = dataSnapshot.child("ProfileIconUrl").getValue(String.class);
                System.out.println("User Email " + userEmail);

                User user = new User();
                user.setUserEmail(userEmail);
                user.setUserName(userName);
                user.setUserID(userID);
                //user.setUserProfileIcon(profileIcon);
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
        RecyclerView usersListView = (RecyclerView) contentView.findViewById(R.id.users_recycler_view);

        ParticipantsSheetAdapter participantsAdapter = new ParticipantsSheetAdapter(userList);
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getActivity().getApplicationContext());
        usersListView.setLayoutManager(mListManager);
        usersListView.setItemAnimator(new DefaultItemAnimator());
        usersListView.setAdapter(participantsAdapter);

        usersListView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),
                usersListView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        System.out.println("User " + userList.get(position).getUserName());

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));
    }
}