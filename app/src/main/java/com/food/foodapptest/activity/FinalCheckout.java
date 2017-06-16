package com.food.foodapptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.food.foodapptest.R;
import com.food.foodapptest.adapter.FinalCartItemFeedAdapter;
import com.food.foodapptest.model.FeedItemSubCategory;
import com.food.foodapptest.util.FinalCartCommunicator;
import com.food.foodapptest.util.FragmentCommunicator;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FinalCheckout extends AppCompatActivity implements FinalCartCommunicator{
    FinalCartItemFeedAdapter listAdapter;
    ListView listView;
    TextView tvTotalPriceFinalCart,priceAmountFooter,amountPayableFooter;
    View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_checkout);
        setupLayoutElements();
    }

    private void setupLayoutElements() {
        tvTotalPriceFinalCart = (TextView) findViewById(R.id.tv_total_price_final_cart);
        listView = (ListView) findViewById(R.id.listCartItemInFinalCheckOut);
        listAdapter = new FinalCartItemFeedAdapter(this,MainActivity.cartItems);
        footer = LayoutInflater.from(this).inflate(R.layout.footer_cart_list, null);
        priceAmountFooter = (TextView) footer.findViewById(R.id.priceAmountFooter);
        amountPayableFooter = (TextView) footer.findViewById(R.id.amountPayableFooter);
        priceAmountFooter.setText(String.valueOf(MainActivity.totalPriceInCart()));
        amountPayableFooter.setText(String.valueOf(MainActivity.totalPriceInCart()));
        tvTotalPriceFinalCart.setText(String.valueOf(MainActivity.totalPriceInCart()));
        listView.addFooterView(footer);
        listView.setAdapter(listAdapter);
    }


    @Override
    public void communicate() {
        tvTotalPriceFinalCart.setText(String.valueOf(MainActivity.totalPriceInCart()));
    }
}
