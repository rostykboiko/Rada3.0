package com.springcamp.rostykboiko.rada3.shared.utlils.firebaseMessaging;

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
import com.springcamp.rostykboiko.rada3.editor.presenter.BottomSheet;
import com.springcamp.rostykboiko.rada3.main.presenter.RecyclerTouchListener;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

import java.util.ArrayList;

public class SurveyDialogActivity extends AppCompatActivity {
    private Survey survey;
    private ArrayList<Survey> surveyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity_survey);


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

    private void initSurveyById(final String surveyId) {
        DatabaseReference mCurrentSurvey = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Survey");

        mCurrentSurvey.orderByChild(surveyId).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().equals(surveyId)) {
                            System.out.println("dataSnapshot.getkey() - " + dataSnapshot.getKey());
                            String surveyTitle = dataSnapshot.child("Title").getValue(String.class);
                            System.out.println("Survey title " + surveyTitle);
                            survey.setSurveyTitle(surveyTitle);

                            for (DataSnapshot child : dataSnapshot.child("Options").getChildren())
                                survey.getSurveyOptionList().add(child.getValue().toString());

                            surveyList.add(survey);
                            survey = new Survey();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void initRecyclerView() {
        final RecyclerView usersListView = (RecyclerView) findViewById(R.id.users_recycler_view);

       // ParticipantsSheetAdapter participantsAdapter = new ParticipantsSheetAdapter(userList);
        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        usersListView.setLayoutManager(mListManager);
        usersListView.setItemAnimator(new DefaultItemAnimator());
        //usersListView.setAdapter(optionListAdapter);

        usersListView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                usersListView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        finish();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));
    }
}
