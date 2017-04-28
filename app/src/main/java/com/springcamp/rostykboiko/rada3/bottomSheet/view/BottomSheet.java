package com.springcamp.rostykboiko.rada3.bottomSheet.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.editor.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.main.view.RecyclerTouchListener;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheet extends AppCompatActivity {
    private InputMethodManager inputMethodManager;
    private BottomSheetBehavior bottomSheet;
    private ParticipantsSheetAdapter participantsSheetAdapter;

    @Nullable
    private ArrayList<User> userList;

    @Nullable
    private Survey survey;

    @BindView(R.id.search_view)
    EditText searchBar;

    @BindView(R.id.toolbar)
    LinearLayout toolbar;

    @OnClick(R.id.backBtn)
    void okClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDim(true);
        setContentView(R.layout.activity_bottom_sheet);

        ButterKnife.bind(this);

        bottomSheet = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        getIntentJson();
        initBehavior();
        initSearchBar();
        initRecyclerView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_HIDDEN, 0);

    }

    @Override
    protected void onPause() {
        super.onPause();
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_HIDDEN, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_HIDDEN, 0);

    }

    private void getIntentJson(){
        String jsonUserList = getIntent().getExtras().getString("UserList");
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        userList = new Gson().fromJson(jsonUserList, type);

        String jsonSurvey = getIntent().getExtras().getString("surveyJson");
        type = new TypeToken<Survey>() {
        }.getType();
        survey = new Gson().fromJson(jsonSurvey, type);
    }

    private void initBehavior() {
        bottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        String jsonSurvey = new Gson().toJson(survey);

                        startActivity(new Intent(BottomSheet.this, EditorActivity.class)
                                .putExtra("surveyJson", jsonSurvey)
                                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));

                        toolbar.setVisibility(View.GONE);
                        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);

                        finish();
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        searchBar.setFocusable(true);
                        searchBar.setFocusableInTouchMode(true);
                        searchBar.requestFocus();

                        toolbar.setVisibility(View.VISIBLE);
                        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_SHOWN, 0);

                        setStatusBarDim(false);
                        break;
                    default:
                        toolbar.setVisibility(View.GONE);
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

    private void initSearchBar() {
        searchBar.setFocusable(false);

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable newText) {
                ArrayList<User> newList = new ArrayList<>();
                for (User user : userList) {
                    if (user.getUserEmail().contains(newText) || user.getUserName().contains(newText))
                        newList.add(user);
                }
                participantsSheetAdapter.setFilter(newList);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView usersListView = (RecyclerView) findViewById(R.id.users_recycler_view);

        participantsSheetAdapter = new ParticipantsSheetAdapter(survey, userList);

        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(getApplicationContext());
        usersListView.setLayoutManager(mListManager);
        usersListView.setItemAnimator(new DefaultItemAnimator());
        usersListView.setAdapter(participantsSheetAdapter);

        usersListView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                usersListView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (!survey.getParticipantsList().contains(userList.get(position).getAccountID())) {
                            userList.get(position).getUserSurveys().add(survey.getSurveyID());
                            survey.getParticipantsList().add(userList.get(position).getAccountID());

                        } else if (survey.getParticipantsList().contains(userList.get(position).getAccountID())) {
                            userList.get(position).getUserSurveys().remove(survey.getSurveyID());
                            survey.getParticipantsList().remove(userList.get(position).getAccountID());

                        }
                        participantsSheetAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));
    }

    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (dim) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentSecond));
            }
        }
    }
}