package com.food.foodapptest.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.food.foodapptest.R;
import com.food.foodapptest.adapter.ExpandableListAdapter;
import com.food.foodapptest.model.FeedItemCategory;

import java.util.ArrayList;

public class FragmentChild extends Fragment {

    ArrayList<FeedItemCategory> feedItemCategories;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    public FragmentChild() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        Bundle bundle = getArguments();
        feedItemCategories = (ArrayList<FeedItemCategory>) bundle.getSerializable("feedItemsCategories");
        setupLayoutElements(view);
        return view;
    }

    private void setupLayoutElements(View view) {
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        listAdapter = new ExpandableListAdapter(getActivity(),feedItemCategories);
        expListView.setAdapter(listAdapter);
    }



}
