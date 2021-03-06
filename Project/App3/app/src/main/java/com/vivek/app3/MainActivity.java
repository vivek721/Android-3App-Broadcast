package com.vivek.app3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements
        ShowsListFragment.ListSelectionListener {
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String INTENT_ACTION = "com.vivek.app.showWiki";
    private static final String MY_PERMISSION = "edu.uic.cs478.s19.kaboom";
    private static final String TAG = "App3_MainActivity";
    public static String[] showTitleArray;
    public static String[] imageArray;
    public static String[] urlArray;
    // Keep shown index in activity to save and restore state
    public static int mShownIndex = -1;
    public static boolean imageAdded = false;
    // Fragment objects
    private final ImageFragment imageFragment = new ImageFragment();
    private final ShowsListFragment showsListFragment = new ShowsListFragment();
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

        // Get the string arrays with the show name, URL and image of the show
        showTitleArray = getResources().getStringArray(R.array.ShowTitles);
        urlArray = getResources().getStringArray(R.array.URLlist);
        imageArray = getResources().getStringArray(R.array.imageList);

        setContentView(R.layout.activity_main);

        // Get references to the TitleFragment and to the QuotesFragment
        showListFrameLayout = (FrameLayout) findViewById(R.id.showListFrame);
        imageFrameLayout = (FrameLayout) findViewById(R.id.imageFrame);

        fragmentManager = getSupportFragmentManager();

        // Retrieve old state if present
        if (savedInstanceState != null) {
            mShownIndex = savedInstanceState.getInt("index");
            imageAdded = savedInstanceState.getBoolean("imageAdded");
        }

        Log.i(TAG, "onCreate: " + mShownIndex + " " + imageAdded);
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

        //Setup Layout when the orientation is changed
        setLayout();
    }

    private void setLayout() {

        // Default view when nothing is selected
        showListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                0, MATCH_PARENT, 1f));
        imageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                MATCH_PARENT, 0f));

        int orientation = getResources().getConfiguration().orientation;
        if (imageFragment.isAdded() || imageAdded) {
            Log.i(TAG, "setLayout: inside lreasd");
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In LANDSCAPE mode
                showListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT, 1f));
                imageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                // In PORTRAIT mode
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

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            fragmentManager.executePendingTransactions();
        }
        Log.i(TAG, "onListSelection: " + index);
        // Tell the imageFragment to show the quote string at position index
        imageFragment.showImageAtIndex(index);
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
                // send ordered broadcast when this option is selected
                try {
                    url = urlArray[mShownIndex];
                    Intent intent = new Intent();
                    intent.setAction(INTENT_ACTION);
                    intent.putExtra("url", url);
                    sendOrderedBroadcast(intent, MY_PERMISSION);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No Show Selected",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.exit:
                // Exit the app when this option is selected
                this.finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Saving the value of the selected list item and checking if the image is added
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("index", mShownIndex);
        bundle.putBoolean("imageAdded", imageAdded);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShownIndex = -1;
        imageAdded = false;
    }
}