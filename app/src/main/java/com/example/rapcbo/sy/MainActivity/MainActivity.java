package com.example.rapcbo.sy.MainActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.rapcbo.sy.Login.LoginActivity;
import com.example.rapcbo.sy.Messages.Messages;
import com.example.rapcbo.sy.Profile.Profil;
import com.example.rapcbo.sy.Profile.ViewCommentsFragment;
import com.example.rapcbo.sy.R;
import com.example.rapcbo.sy.Search.SearchActivity;
import com.example.rapcbo.sy.Share.ShareActivity;
import com.example.rapcbo.sy.Show.Photo;
import com.example.rapcbo.sy.Storis.Storis;
import com.example.rapcbo.sy.classes.PaqerAdapter;
import com.example.rapcbo.sy.classes.UniversalImageLoader;
import com.example.rapcbo.sy.notification.notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,Tab2.OnFragmentInteractionListener
                                             { // MainfeedListAdapter.OnLoadMoreItemsListener{

   // @Override
   /* public void onLoadMoreItems() {

        Log.d(TAG, "onLoadMoreItems: displaying more photos");
        Tab1 fragment = (Tab1)getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.viewpager_container + ":" + mViewPager.getCurrentItem());
        if(fragment != null){
            fragment.displayMorePhotos();
        }
    }   */

    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
  //  private static final int TAB1 = 1;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Uri mMediaUri;

    //widgets
    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starting.");
        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);
        mFrameLayout = (FrameLayout) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);

        initImageLoader();
        setupFirebaseAuth();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.s));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.t));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //toolbar.setNavigationIcon(R.drawable.fffff);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PaqerAdapter adapter = new PaqerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    public void onCommentThreadSelected(Photo photo,String callingActivity) {
        Log.d(TAG, "onCommentThreadSelected: selected a coemment thread");

        ViewCommentsFragment fragment = new ViewCommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo), photo);
        args.putString(getString(R.string.home_activity), getString(R.string.home_activity));
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_comments_fragment));
        transaction.commit();
    }


    public void hideLayout(){
        Log.d(TAG, "hideLayout: hiding layout");
        mRelativeLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }

    public void showLayout(){
        Log.d(TAG, "hideLayout: showing layout");
        mRelativeLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFrameLayout.getVisibility() == View.VISIBLE){
            showLayout();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){

           // case R.id.t:
             //   Intent i = new Intent(MainActivity.this, Tweet.class);
               // startActivity(i);
                //break;
            case R.id.s:
                Intent u = new Intent(MainActivity.this, Storis.class);
                startActivity(u);
                break;
            case R.id.ch:
                Intent y = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(y);
                break;
            case R.id.n:
                Intent t = new Intent(MainActivity.this, notification.class);
                startActivity(t);
                break;
            case R.id.m:
                Intent m = new Intent(MainActivity.this, Messages.class);
                startActivity(m);
                break;
            case R.id.p:
                Intent e = new Intent(MainActivity.this, Profil.class);
                startActivity(e);
                break;
            case R.id.sh:
                Intent sh = new Intent(MainActivity.this, ShareActivity.class);
                startActivity(sh);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

   /* ------------------------------------ Firebase ---------------------------------------------
            */

    /**
     * checks to see if the @param 'user' is logged in
     * @param //user
     */
    private void checkCurrentUser(FirebaseUser user){
       Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            startActivity(intent);
        }
    }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
       Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                   Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
       // mViewPager.setCurrentItem(TAB1);
        checkCurrentUser(mAuth.getCurrentUser());

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



}
