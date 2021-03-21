package com.vivek.app3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private static final String TAG = "App3_ImageFragment";

    private ImageView mImageView = null;
    private int mCurrIdx = -1;
    private int mImageArrayLength;

    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the Quote string at position newIndex
    void showImageAtIndex(int newIndex) {
        mCurrIdx = newIndex;
        Log.i(TAG, "showImageAtIndex: " + newIndex);
        if (newIndex < 0 || newIndex >= mImageArrayLength)
            return;
        Log.i(TAG, "showImageAtIndex: ");
        int imageResource = getResources().getIdentifier("@drawable/" + MainActivity.imageArray[mCurrIdx], null, "com.vivek.app3");
        Log.i(TAG, "showImageAtIndex: " + imageResource);
        mImageView.setImageResource(imageResource);

    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        MainActivity.imageAdded = true;
        // Inflate the layout defined in quote_fragment.xml
        // The last parameter is false because the returned view does not need to
        // be attached to the container ViewGroup
        return inflater.inflate(R.layout.imageview, container, false);
    }

    // Set up some information about the mQuoteView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");

        super.onActivityCreated(savedInstanceState);
        mImageView = (ImageView) getActivity().findViewById(R.id.imageView);
        mImageArrayLength = MainActivity.imageArray.length;
        if (MainActivity.mShownIndex != -1) {
            Log.i(TAG, "onActivityCreated: " + MainActivity.mShownIndex);
            showImageAtIndex(MainActivity.mShownIndex);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        MainActivity.imageAdded = false;
    }

}
