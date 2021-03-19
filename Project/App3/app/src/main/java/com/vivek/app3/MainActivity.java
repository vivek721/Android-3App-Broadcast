package com.vivek.app3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ShowsListFragment.ListSelectionListener {
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    // intent to launch app1
    private static final String INTENT_ACTION = "com.vivek.app.showWiki";
    private static final String TAG = "MainActivity";
    public static String[] showTitleArray;
    public static String[] imageArray;
    public static String[] urlArray;
    // Fragment objects
    private final ImageFragment imageFragment = new ImageFragment();
    private final ShowsListFragment showsListFragment = new ShowsListFragment();
    // Keep shown index in activity to save and restore state
    int mShownIndex = -1;
    private FrameLayout showListFrameLayout, imageFrameLayout;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //handling Actionbar Icon and title
        getSupportActionBar().setTitle(" Application 3");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.appicon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // Get the string arrays with the show name and image location
        showTitleArray = getResources().getStringArray(R.array.ShowTitles);
        urlArray = getResources().getStringArray(R.array.URLlist);
        imageArray = getResources().getStringArray(R.array.imageList);

        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: ");

        // Get references to the TitleFragment and to the QuotesFragment
        showListFrameLayout = (FrameLayout) findViewById(R.id.showListFrame);
        imageFrameLayout = (FrameLayout) findViewById(R.id.imageFrame);


        fragmentManager = getSupportFragmentManager();

        // Begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the ShowFragment
        fragmentTransaction.replace(R.id.showListFrame, showsListFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        fragmentManager.addOnBackStackChangedListener(

                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {
        int orientation = getResources().getConfiguration().orientation;
        if (!imageFragment.isAdded()) {
            // Make the ShowsListFragment occupy the entire layout
            showListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0, MATCH_PARENT, 1f));
            imageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 0f));
        } else {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                // Determine whether the imageFragment has been added
                showListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
                imageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            } else {
                showListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 0f));
                imageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
            }
        }

    }

    // Implement Java interface ListSelectionListener defined in TitlesFragment
    // Called by TitlesFragment when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {

        mShownIndex = index;

        // If the QuoteFragment has not been added, add it now
        if (!imageFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();

            // Add the QuoteFragment to the layout
            fragmentTransaction.add(R.id.imageFrame,
                    imageFragment);
            imageFragment.setRetainInstance(true);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            fragmentManager.executePendingTransactions();
        }

        if (imageFragment.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index
            imageFragment.showImageAtIndex(index);
        }
    }

    // method for creating options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    // method for handling options menu click operations
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String url;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.changeapp:
                try {
                    url = urlArray[mShownIndex];
                } catch (Exception e) {
                    url = null;
                }
                Intent intent = new Intent();
                intent.setAction(INTENT_ACTION);
                intent.putExtra("url", url);
                sendBroadcast(intent);
                return true;
            case R.id.exit:
                this.finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}