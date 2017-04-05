package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
import com.springcamp.rostykboiko.rada3.intro.view.MainIntroActivity;
import com.springcamp.rostykboiko.rada3.main.presenter.MainPresenter;
import com.springcamp.rostykboiko.rada3.main.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.main.presenter.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.main.presenter.RecyclerTouchListener;
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

public class MainActivity extends AppCompatActivity
        implements MainContract.View {
    SessionManager session;
    private Option option = new Option();
    private Survey survey = new Survey();
    private RecyclerView cardRecyclerView;
    private ArrayList<Survey> surveyList = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private CardsAdaptor cardsAdaptor;
    private FloatingActionButton fab;
    private Drawer mDrawer = null;
    private Toolbar toolbar;
    private ImageView navDrawerBtn;

    @Nullable
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        initUserData();

        initNavDrawer();
        initViewItems();
        initCardView();
        initFireBase();
        initClickListeners();
    }

    private void initUserData(){
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        GoogleAccountAdapter.setUserName(user.get(SessionManager.KEY_NAME));
        GoogleAccountAdapter.setUserEmail(user.get(SessionManager.KEY_EMAIL));
        GoogleAccountAdapter.setUserID(user.get(SessionManager.KEY_UID));
        GoogleAccountAdapter.setAccountID(user.get(SessionManager.KEY_ACCOUNTID));
        GoogleAccountAdapter.setDeviceToken(user.get(SessionManager.KEY_TOKEN));
        GoogleAccountAdapter.setProfileIcon(user.get(SessionManager.KEY_ICON));
    }
    private void initViewItems() {
        navDrawerBtn = (ImageView) findViewById(R.id.drawerBtn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                survey = new Survey();
                EditorActivity.launchActivity(MainActivity.this,
                        survey);
                finish();
            }
        });

        cardRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(),
                cardRecyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        final String surveyId = surveyList.get(position).getSurveyID();

                        database.getReference()
                                .child("User")
                                .child(GoogleAccountAdapter.getAccountID())
                                .child("Surveys")
                                .child(surveyList.get(position).getSurveyID())
                                .removeValue();

                        DatabaseReference mCurrentSurvey = FirebaseDatabase
                                .getInstance()
                                .getReference()
                                .child("Survey")
                                .child(surveyList.get(position).getSurveyID())
                                .child("Creator");

                        System.out.println("SurveyID surveyList " + surveyList);


                        mCurrentSurvey.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                System.out.println("SurveyID getAccountID " + GoogleAccountAdapter
                                        .getAccountID());
                                System.out.println("SurveyID getValue " + dataSnapshot.getValue(String.class));

                                if (dataSnapshot.getValue(String.class) != null && dataSnapshot.getValue(String.class)
                                        .equals(GoogleAccountAdapter
                                                .getAccountID())) {
                                    removeSurvey(surveyId);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        surveyList.remove(position);
                        cardsAdaptor.notifyDataSetChanged();
                    }
                }));

        navDrawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer();
            }
        });
    }

    private void removeSurvey(String surveyId) {
        System.out.println("SurveyID surveyList " + surveyId);

        database.getReference()
                .child("Survey")
                .child(surveyId)
                .removeValue();
    }

    /* List of Cards Start */
    private void initFireBase() {
        DatabaseReference mCurrentUserRef;
        if (GoogleAccountAdapter.getUserID() != null) {
            mCurrentUserRef = FirebaseDatabase.getInstance().getReference()
                    .child("User").child(GoogleAccountAdapter.getAccountID()).child("Surveys");
            System.out.println("UID " + GoogleAccountAdapter.getUserID());

            Query mQueryUser = mCurrentUserRef;
            mQueryUser.orderByValue().addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    cardsAdaptor.notifyDataSetChanged();

                    System.out.println("surveysIDs " + dataSnapshot.getKey());

                    initSurveyById(dataSnapshot.getKey());
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

    }

    private void initSurveyById(final String surveyId) {
        DatabaseReference mCurrentSurvey = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Survey");

        mCurrentSurvey.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().equals(surveyId)) {
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

                            surveyList.add(survey);
                            cardsAdaptor.notifyDataSetChanged();
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

    private void initCardView() {
        cardsAdaptor = new CardsAdaptor(this, surveyList);
        cardRecyclerView = (RecyclerView) findViewById(R.id.card_recycler);

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
                                .withIcon(GoogleAccountAdapter.getProfileIcon())
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
                                            onResume();
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
        finish();
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
}