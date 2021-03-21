package com.vivek.app3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

public class ShowsListFragment extends ListFragment {

    static final String OLD_POSITION = "oldPos";
    private static final String TAG = "App3_ShowsListFragment";
    Integer mOldPosition = null;
    private TextView showName = null;
    private ListSelectionListener mListener = null;

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        // Indicates the selected item has been checked
        // Important: Must have setChoiceMode to either CHOICE_MODE_SINGLE or MULTIPLE
        getListView().setItemChecked(pos, true);

        // Inform the ImageFragment that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);

        try {
            // Set the ListSelectionListener for communicating with the ImageFragment
            mListener = (ListSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnListSelectionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        setListAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.showlist, MainActivity.showTitleArray));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //get index value after orientation change
        int mIndex = MainActivity.mShownIndex;
        if(mIndex != -1) {
            Log.i(TAG, "onActivityCreated: " + mIndex);
            getListView().setItemChecked(mIndex, true);
        }
    }

    // Callback interface that allows this Fragment to notify the ImageFragment when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

}
