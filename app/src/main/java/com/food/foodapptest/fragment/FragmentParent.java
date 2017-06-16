package com.food.foodapptest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.food.foodapptest.R;
import com.food.foodapptest.activity.FinalCheckout;
import com.food.foodapptest.adapter.FinalCartItemFeedAdapter;
import com.food.foodapptest.adapter.ViewPagerAdapter;
import com.food.foodapptest.model.FeedItemCategory;
import com.food.foodapptest.model.FeedItemSubCategory;

import java.util.ArrayList;

/**
 * Created by HP on 14-06-2017.
 */

public class FragmentParent extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private TextView tvItemInfo;
    private android.support.v7.widget.Toolbar toolbarBottom;

    public FragmentParent(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        setupLayoutElements(view);
        return view;
    }

    private void setupLayoutElements(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.food_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.food_tab_layout);
        tvItemInfo = (TextView) view.findViewById(R.id.tv_item_info);
        toolbarBottom = (android.support.v7.widget.Toolbar)view.findViewById(R.id.toolbar_bottom);
        adapter = new ViewPagerAdapter(getFragmentManager(), getActivity(), viewPager, tabLayout);
        viewPager.setAdapter(adapter);
        toolbarBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FinalCheckout.class);
                getActivity().startActivity(intent);
            }
        });
    }

    int selectedTabPosition;
//
//    private void setEvents() {
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                super.onTabSelected(tab);
//                viewPager.setCurrentItem(tab.getPosition());
//                selectedTabPosition = viewPager.getCurrentItem();
//                Log.d("Selected", "Selected " + tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                super.onTabUnselected(tab);
//                Log.d("Unselected", "Unselected " + tab.getPosition());
//            }
//        });
//    }

    public void addPage(String pagename, ArrayList<FeedItemCategory> feedItemCategories) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("feedItemsCategories",feedItemCategories);
        FragmentChild fragmentChild = new FragmentChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);
        setupTabLayout();
    }


    public void setupTabLayout() {
        selectedTabPosition = viewPager.getCurrentItem();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }
    }

    public void changeTextInBottomToolbar(String units,String price){
        toolbarBottom.setVisibility(View.VISIBLE);
        tvItemInfo.setText(units + " item(s) in cart Rs. "+ price);
    }


}
