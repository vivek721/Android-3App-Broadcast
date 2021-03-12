package com.vivek.app3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ShowFragment.ListSelectionListener {
    public static String[] showTitleArray;
    public static Integer[] imageArray = new Integer[4];

    // UB: Keep shown index in activity to save and restore state
    int mShownIndex = -1 ;
    static String OLD_ITEM = "old_item" ;
    private ShowFragment showFragment = new ShowFragment();
    private final ImageFragment imageFragment = new ImageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the string arrays with the show name and image location
        showTitleArray = getResources().getStringArray(R.array.ShowTitles);
//        imageArray = getResources().getStringArray(R.array.ShowImage);
        imageArray[0] = R.drawable.friends;
        imageArray[1] = R.drawable.breakingbad;
        imageArray[2] = R.drawable.gameofthrones;
        imageArray[3] = R.drawable.office;
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the ShowFragment
        fragmentTransaction.replace(R.id.show, showFragment) ;

        // Add the ImageFragment
        fragmentTransaction.replace(R.id.imageFrame, imageFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.app:
                return true;
            case R.id.exit:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListSelection(int index) {
        Intent intent = new Intent(this, ImageActivity.class);

        // if (ImageFragment.getShownIndex() != index) {
        if (mShownIndex != index) {
            // Tell the ImageFragment to show the quote string at position index
            imageFragment.showImageAtIndex(index);
            // and update the shownIndex
            mShownIndex = index ;
        }
    }
}