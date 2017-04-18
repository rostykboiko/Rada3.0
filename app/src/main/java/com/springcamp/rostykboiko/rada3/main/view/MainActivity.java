package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.springcamp.rostykboiko.rada3.answer.view.AnswerDialogActivity;
import com.springcamp.rostykboiko.rada3.api.model.Question;
import com.springcamp.rostykboiko.rada3.intro.view.MainIntroActivity;
import com.springcamp.rostykboiko.rada3.main.presenter.MainPresenter;
import com.springcamp.rostykboiko.rada3.main.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.receiver.QuestionReceiver;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.editor.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.login.view.LoginActivity;
import com.springcamp.rostykboiko.rada3.settings.view.SettingsActivity;
import com.springcamp.rostykboiko.rada3.shared.utlils.SessionManager;
import com.springcamp.rostykboiko.rada3.shared.utlils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements MainContract.View {
    private SessionManager session;
    private Option option = new Option();
    private Survey survey = new Survey();
    private ArrayList<Survey> surveyList = new ArrayList<>();
    private CardsAdaptor cardsAdaptor;
    private Drawer mDrawer = null;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @NonNull
    private QuestionReceiver questionReceiver;

    @Nullable
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);
        session = new SessionManager(getApplicationContext());

        initUserData();
        initFireBase();
        initCardView();
        initNavDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        questionReceiver = new QuestionReceiver(new QuestionReceiver.QuestionReceivedCallback() {
            @Override
            public void onQuestionReceived(@NonNull Question question) {
                if (presenter != null) {
                    presenter.receivedQuestion();
                }
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(questionReceiver,
                new IntentFilter(QuestionReceiver.QUESTION_RECEIVED_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(questionReceiver);
    }

    private void initUserData() {
        HashMap<String, String> user = session.getUserDetails();

        GoogleAccountAdapter.setUserID(user.get(SessionManager.KEY_UID));
        GoogleAccountAdapter.setUserName(user.get(SessionManager.KEY_NAME));
        GoogleAccountAdapter.setUserEmail(user.get(SessionManager.KEY_EMAIL));
        GoogleAccountAdapter.setAccountID(user.get(SessionManager.KEY_ACCOUNTID));
        GoogleAccountAdapter.setDeviceToken(user.get(SessionManager.KEY_TOKEN));
        GoogleAccountAdapter.setProfileIcon(user.get(SessionManager.KEY_ICON));
    }

    /* List of Cards Start */
    private void initFireBase() {
        DatabaseReference mCurrentUserRef;
        if (GoogleAccountAdapter.getUserID() != null) {
            mCurrentUserRef = FirebaseDatabase.getInstance().getReference()
                    .child("User").child(GoogleAccountAdapter.getAccountID()).child("Surveys");

            Query mQueryUser = mCurrentUserRef;
            mQueryUser.orderByValue().addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    initSurveyById(dataSnapshot.getKey());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    initSurveyById(dataSnapshot.getKey());
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    initSurveyById(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void initSurveyById(final String surveyId) {
        final DatabaseReference mCurrentSurvey = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Survey");

        mCurrentSurvey.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().equals(surveyId)) {
                            surveyList = new ArrayList<>();
                            survey.setSurveyID(dataSnapshot.getKey());
                            String surveyTitle = dataSnapshot.child("Title").getValue(String.class);
                            survey.setSurveyTitle(surveyTitle);

                            for (DataSnapshot optionSnapshot : dataSnapshot.child("Options").getChildren()) {
                                option.setOptionTitle(optionSnapshot.getValue().toString());
                                option.setAnswerCounter(dataSnapshot
                                        .child("Answers")
                                        .child(optionSnapshot.getKey())
                                        .getChildrenCount() - 1);

                                survey.getSurveyOptionList().add(option);
                                option = new Option();
                            }

                            survey.setParticipantsCount(Utils.longToInt(dataSnapshot
                                    .child("Participants")
                                    .getChildrenCount()));

                            survey.setSurveySingleOption(dataSnapshot
                                    .child("One Positive Option")
                                    .getValue(Boolean.class));

                            surveyList.add(survey);
                            cardsAdaptor.setSurveyList(surveyList);

                            survey = new Survey();
                            cardsAdaptor.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        onChildAdded(dataSnapshot, s);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        onChildAdded(dataSnapshot, null);

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void initCardView() {
        cardsAdaptor = new CardsAdaptor(this, new CardsAdaptor.QuestionsCardCallback() {
            @Override
            public void onCardDeleted(@NonNull Survey survey) {
                final String surveyId = survey.getSurveyID();

                FirebaseDatabase.getInstance().getReference()
                        .child("Survey")
                        .child(surveyId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                System.out.println("onBtn remove value: " + dataSnapshot.child("Creator").getValue());
                                if (dataSnapshot.child("Creator").getValue() != null
                                        &&
                                        dataSnapshot.child("Creator")
                                                .getValue().equals(GoogleAccountAdapter.getAccountID())) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Survey")
                                            .child(surveyId)
                                            .removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                FirebaseDatabase.getInstance().getReference()
                        .child("User")
                        .child(GoogleAccountAdapter.getAccountID())
                        .child("Surveys")
                        .child(surveyId)
                        .removeValue();
            }

            @Override
            public void onCardClick(@NonNull Survey survey) {
                System.out.println("onCardClick holder position " + survey.getSurveyID());
                Gson gson = new Gson();
                String json = gson.toJson(survey);

                startActivity(new Intent(MainActivity.this, EditorActivity.class)
                        .putExtra("surveyJson", json));
            }
        });

        RecyclerView cardRecyclerView = (RecyclerView) findViewById(R.id.card_recycler);

        RecyclerView.LayoutManager mCardManager = new LinearLayoutManager(getApplicationContext());
        cardRecyclerView.setLayoutManager(mCardManager);
        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cardRecyclerView.setAdapter(cardsAdaptor);
    }
    /* List of Cards End */

    /* NavDrawer Start */
    private AccountHeader initNawDrawerHeader() {
        //          Image Download
        if (GoogleAccountAdapter.getProfileIcon() != null) {
            DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                    Glide.with(imageView.getContext()).load(GoogleAccountAdapter.getProfileIcon()).placeholder(placeholder).into(imageView);
                }

                @Override
                public void cancel(ImageView imageView) {
                    Glide.clear(imageView);
                }

                @Override
                public Drawable placeholder(Context ctx, String tag) {

                    if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                        return DrawerUIUtils.getPlaceHolder(ctx);
                    } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                        return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.colorActiveSwitch).sizeDp(56);
                    } else if ("customUrlItem".equals(tag)) {
                        return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.colorActiveSwitch).sizeDp(56);
                    }
                    return super.placeholder(ctx, tag);
                }
            });
        }

        String iconURL = "";
        if (GoogleAccountAdapter.getProfileIcon() != null)
            iconURL = GoogleAccountAdapter.getProfileIcon();
        return new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withTextColorRes(R.color.colorPrimaryText)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabled(false)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(GoogleAccountAdapter.getUserName())
                                .withEmail(GoogleAccountAdapter.getUserEmail())
                                .withIcon(iconURL)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }

    private void initNavDrawer() {
        final String signInOut;
        if (GoogleAccountAdapter.getUserEmail() != null)
            signInOut = "Sign Out";
        else
            signInOut = "Sign in";

        AccountHeader mHeader = initNawDrawerHeader();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withDrawerWidthDp(275)
                .withAccountHeader(mHeader)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName("Home")
                                .withIcon(R.drawable.ic_material_home)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        mDrawer.closeDrawer();
                                        return false;
                                    }
                                }),
                        new PrimaryDrawerItem()
                                .withName("Add new Event")
                                .withIcon(R.drawable.ic_material_calendar)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        mDrawer.closeDrawer();
                                        if (presenter != null && survey != null) {
                                            presenter.showEditor(survey);
                                        }
                                        return false;
                                    }
                                }),
                        new SectionDrawerItem()
                                .withName("Groups"),
                        new PrimaryDrawerItem()
                                .withName("Main group")
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(
                                            View view, int position, IDrawerItem drawerItem) {
                                        mDrawer.closeDrawer();
                                        // do something with  this peace of sh**
                                        return false;
                                    }
                                }),
                        new PrimaryDrawerItem()
                                .withName("Add new group")
                                .withIcon(R.drawable.ic_material_addgroup)
                                .withOnDrawerItemClickListener
                                        (new Drawer.OnDrawerItemClickListener() {
                                            @Override
                                            public boolean onItemClick(View view, int position,
                                                                       IDrawerItem drawerItem) {
                                                mDrawer.removeItem(position);
                                                addNewDrawerItem(position);
                                                return false;
                                            }
                                        })
                )
                .addStickyDrawerItems(
                        new SecondaryDrawerItem()
                                .withName(signInOut)
                                .withIcon(R.drawable.ic_material_accsircle)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        mDrawer.closeDrawer();
                                        if (GoogleAccountAdapter.getUserEmail() == null)
                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        else {
                                            session.logoutUser();
                                            GoogleAccountAdapter.logOut();
                                            surveyList.clear();
                                            cardsAdaptor.notifyDataSetChanged();
                                            initNavDrawer();
                                        }
                                        return false;
                                    }
                                }),
                        new SecondaryDrawerItem()
                                .withName("Settings")
                                .withIcon(R.drawable.ic_material_cog)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        mDrawer.closeDrawer();
                                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                                        return false;
                                    }
                                })
                )
                .build();
    }

    private void addNewDrawerItem(int position) {
        mDrawer.getDrawerItem(position);
        mDrawer.addItem(new PrimaryDrawerItem().withName("newItem")
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mDrawer.removeItem(position);
                        return false;
                    }
                })
        );
    }
    /* NavDrawer End */

    @Override
    public void showProgress() {
    }

    @Override
    public void showEditor(@NonNull Survey survey) {
        EditorActivity.launchActivity(this, survey);
    }

    @Override
    public void showReceivedQuestion() {
        AnswerDialogActivity.launchActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.action_delete:
                startActivity(new Intent(MainActivity.this, MainIntroActivity.class));
                break;
            case R.id.action_profile:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    void okClick() {
        survey = new Survey();
        EditorActivity.launchActivity(MainActivity.this, survey);
    }

}