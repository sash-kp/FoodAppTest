package com.food.foodapptest.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.foodapptest.R;
import com.food.foodapptest.activity.MainActivity;
import com.food.foodapptest.model.FeedItemSubCategory;
import com.food.foodapptest.util.FinalCartCommunicator;
import com.food.foodapptest.util.FragmentCommunicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 12-06-2017.
 */

public class FinalCartItemFeedAdapter extends BaseAdapter {
    private AppCompatActivity context;
    private LayoutInflater inflater;
    private ArrayList<FeedItemSubCategory> feedItems;
    FinalCartCommunicator finalCartCommunicator;

    public FinalCartItemFeedAdapter(AppCompatActivity context, ArrayList<FeedItemSubCategory> feedItems) {
        this.context = context;
        this.feedItems = feedItems;
        this.finalCartCommunicator = (FinalCartCommunicator) context;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        feedItems.clear();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.item_final_checkout, null);


        ImageView feedImageView = (ImageView) view.findViewById(R.id.productImageInFinalCart);
        ImageView addProduct = (ImageView) view.findViewById(R.id.img_btn_add_in_final_cart);
        ImageView removeProduct = (ImageView) view.findViewById(R.id.img_btn_remove_from_final_cart);

        final FeedItemSubCategory item = feedItems.get(position);

        TextView name = (TextView) view.findViewById(R.id.productNameInFinalCart);
        TextView productPrice = (TextView) view.findViewById(R.id.productPriceInFinalCart);
        TextView totalPricePerProduct = (TextView) view.findViewById(R.id.productFinalPriceInFinalCart);
        TextView productQunantity = (TextView) view.findViewById(R.id.productQuantityInFinalCart);
        final TextView numberOfProducts = (TextView) view.findViewById(R.id.numberOfProductsInFinalCart);

        name.setText(item.getName());
        productPrice.setText(String.valueOf(item.getPrice()));
        productQunantity.setText(item.getUnit());
        numberOfProducts.setText(String.valueOf(item.getNumOfProds()));
        totalPricePerProduct.setText(String.valueOf(item.getPrice() * item.getNumOfProds()));

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setNumOfProds(item.getNumOfProds() + 1);
                MainActivity.addItemToCart(item);
                numberOfProducts.setText(String.valueOf(Integer.parseInt(numberOfProducts.getText().toString()) + 1));
                finalCartCommunicator.communicate();
            }
        });

        removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setNumOfProds(item.getNumOfProds()-1);
                MainActivity.removeItemFromCart(item);
                if(item.getNumOfProds()==0){
                    numberOfProducts.setVisibility(View.GONE);
                } else {
                    numberOfProducts.setText(String.valueOf(Integer.parseInt(numberOfProducts.getText().toString())-1));
                    numberOfProducts.setVisibility(View.VISIBLE);
                }
                finalCartCommunicator.communicate();
            }
        });

        Picasso.with(context)
                .load(item.getImage())
                .into(feedImageView);

        return view;
    }


}
