package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
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
import com.springcamp.rostykboiko.rada3.MainContract;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.main.presenter.CardsAdaptor;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;
import com.springcamp.rostykboiko.rada3.editor.view.EditorActivity;
import com.springcamp.rostykboiko.rada3.login.view.LoginActivity;
import com.springcamp.rostykboiko.rada3.settings.view.SettingsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainContract.View {
    private Survey survey = new Survey();
    private List<Survey> surveyList = new ArrayList<>();
    private List<String> optionslist = new ArrayList<>();
    private CardsAdaptor cardsAdaptor;
    private FloatingActionButton fab;
    private Drawer mDrawer = null;
    private Toolbar toolbar;

    @Nullable
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        initFireBase();
        initNavDrawer();
        initViewItems();
        initClickListeners();
        initCardView();
    }

    private void initViewItems() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

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
                        return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.primary).sizeDp(56);
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
                                        startActivity(new Intent(MainActivity.this, EditorActivity.class));
                                        finish();
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
                                            GoogleAccountAdapter.logOut();
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
        mDrawer.addItem(new PrimaryDrawerItem().withName("newItem").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mDrawer.removeItem(position);
                        return false;
                    }
                })
        );
    }
    /* NavDrawer End */

    /**
     * List of Cards Start
     */

    private void initFireBase() {
        Firebase surveyRef = new Firebase("https://rada3-30775.firebaseio.com/Survey");
        surveyRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String surveyTitle = dataSnapshot.child("Title").getValue(String.class);
                System.out.println("Survey title " + surveyTitle);
                survey.setSurveyTitle(surveyTitle);

                for (DataSnapshot child : dataSnapshot.child("Options").getChildren()) {
                    String fckingOption = child.getValue().toString();
                    optionslist.add(fckingOption);
                    System.out.println("Survey option list: " + fckingOption);
                    survey.getSurveyOptionList().add(fckingOption);
                }

                surveyList.add(survey);
                cardsAdaptor.notifyDataSetChanged();
                survey = new Survey();
                optionslist = new ArrayList<>();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                cardsAdaptor.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void initCardView() {
        cardsAdaptor = new CardsAdaptor(this, surveyList, optionslist);
        RecyclerView cardRecyclerView = (RecyclerView) findViewById(R.id.card_recycler);
        RecyclerView.LayoutManager mCardManager = new GridLayoutManager(this, 2);
        cardRecyclerView.setLayoutManager(mCardManager);
        cardRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cardRecyclerView.setAdapter(cardsAdaptor);
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * List of Cards End
     */

    @Override
    public void showProgress() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void sendMessage()
            throws IOException, JSONException {
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);

        // HTTP request header
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "key=AIzaSyCoKx1rrWKUcCf6QMIyB2viYed_l0RnxtQ");
        con.setRequestMethod("POST");
        con.connect();

        // HTTP request
        JSONObject data = new JSONObject();
        JSONObject notificationFCM = new JSONObject();

        String tokenID = "fM_jRk_3QgE:APA91bG5iZfEZFgDIsQzhUwgj5qJwdOS9_9q3Z-0nfzSX6Q-IzCHZHeN_" +
                "mndFr1TGM2Auk45VxZCGmGU1gQjtqAKlYXVF36YZDOfs2wXpsAcWbjmlouCGFIcZGygyJjLdNSfZREoc1Yg";
        notificationFCM.put("body", "Message sent from device");
        notificationFCM.put("title", "Survey");
        notificationFCM.put("sound", "default");
        notificationFCM.put("priority", "high");
        data.put("notification", notificationFCM);
        data.put("to", tokenID);
        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes());
        os.close();
        System.out.println("Response Code: " + con.getResponseCode());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendMessage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
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