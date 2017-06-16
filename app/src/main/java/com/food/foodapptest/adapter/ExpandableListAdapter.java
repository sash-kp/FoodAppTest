package com.food.foodapptest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.foodapptest.R;
import com.food.foodapptest.activity.MainActivity;
import com.food.foodapptest.model.FeedItemCategory;
import com.food.foodapptest.model.FeedItemSubCategory;
import com.food.foodapptest.util.FragmentCommunicator;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<FeedItemCategory> groups;
    FragmentCommunicator fragmentCommunicator;

    public ExpandableListAdapter(Context context, ArrayList<FeedItemCategory> groups) {
        this._context = context;
        this.groups = groups;
        this.fragmentCommunicator = (FragmentCommunicator)_context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<FeedItemSubCategory> chList = groups.get(groupPosition).getChildren();
        return chList.get(childPosition);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, final View convertView, ViewGroup parent) {
        final View view;
        final FeedItemSubCategory child = (FeedItemSubCategory) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_subcategory, null);
        } else {
            view = convertView;
        }
        ImageView productImageView = (ImageView) view.findViewById(R.id.productImage);
        TextView productName = (TextView) view.findViewById(R.id.productName);
        TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        TextView productQuantity = (TextView) view.findViewById(R.id.productQuantity);
        final TextView numberOfProducts = (TextView) view.findViewById(R.id.numberOfProducts);
        numberOfProducts.setVisibility(View.GONE);
        ImageView addProduct = (ImageView) view.findViewById(R.id.img_btn_add_to_cart);
        ImageView removeProduct = (ImageView) view.findViewById(R.id.img_btn_remove_from_cart);

        productName.setText(child.getName());
        if(child.getPrice()!=-1){
            productPrice.setText(String.valueOf(child.getPrice()));
        }
        if(child.getUnit()!=null){
            productQuantity.setText(String.valueOf(child.getUnit()));
        }

        if(child.getImage()!=null){
            Picasso.with(_context)
                    .load(child.getImage())
                    .config(Bitmap.Config.RGB_565)
                    .into(productImageView);
        }

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.setNumOfProds(child.getNumOfProds()+1);
                MainActivity.addItemToCart(child);
                if(numberOfProducts.getVisibility()==View.GONE){
                    numberOfProducts.setText(String.valueOf(1));
                    numberOfProducts.setVisibility(View.VISIBLE);
                } else {
                    numberOfProducts.setText(String.valueOf(Integer.parseInt(numberOfProducts.getText().toString())+1));
                    numberOfProducts.setVisibility(View.VISIBLE);
                }
                fragmentCommunicator.respond(String.valueOf(MainActivity.numberOfTotalProductsInCart()), String.valueOf(MainActivity.totalPriceInCart()));
            }
        });

        removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.setNumOfProds(child.getNumOfProds()-1);
                MainActivity.removeItemFromCart(child);
                if(child.getNumOfProds()==0){
                    numberOfProducts.setVisibility(View.GONE);
                } else {
                    numberOfProducts.setText(String.valueOf(Integer.parseInt(numberOfProducts.getText().toString())-1));
                    numberOfProducts.setVisibility(View.VISIBLE);
                }
                fragmentCommunicator.respond(String.valueOf(MainActivity.numberOfTotalProductsInCart()), String.valueOf(MainActivity.totalPriceInCart()));

            }
        });


        return view;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<FeedItemSubCategory> chList = groups.get(groupPosition).getChildren();
        if (chList == null) {
            return 0;
        }
        return chList.size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view;
        FeedItemCategory group = (FeedItemCategory) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.item_category, null);
        } else {
            view = convertView;
        }

        TextView tv = (TextView) view.findViewById(R.id.lblListHeader);
        tv.setText(group.getCategoryName());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void clearData() {
        // clear the data
        groups.clear();
    }

}
