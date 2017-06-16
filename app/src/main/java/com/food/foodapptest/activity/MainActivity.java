package com.food.foodapptest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.food.foodapptest.R;
import com.food.foodapptest.fragment.FragmentChild;
import com.food.foodapptest.fragment.FragmentParent;
import com.food.foodapptest.model.FeedItemCategory;
import com.food.foodapptest.model.FeedItemSubCategory;
import com.food.foodapptest.util.AppController;
import com.food.foodapptest.util.FragmentCommunicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.food.foodapptest.util.Constants.BASE_URL;
import static com.food.foodapptest.util.Constants.TIMEOUT_MS;
import static com.food.foodapptest.util.Constants.URL_TO_FETCH_FOOD_ITEMS;

public class MainActivity extends AppCompatActivity implements FragmentCommunicator {

    FragmentParent fragmentParent;
    private ArrayList<FeedItemCategory> feedItemsCategory;
    public static ArrayList<FeedItemSubCategory> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLayoutElements();
        feedItemsCategory = new ArrayList<>();
        fetchFoodItems(URL_TO_FETCH_FOOD_ITEMS);
        cartItems = new ArrayList<>();
    }

    private void addTab(String pageName, ArrayList<FeedItemCategory> feedItemsCategories) {
        fragmentParent.addPage(pageName, feedItemsCategories);
    }


    private void getLayoutElements() {
        fragmentParent = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);
    }

    private void fetchFoodItems(String urlToFetchFoodItems) {
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                urlToFetchFoodItems, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                //Log.d("Response", response.toString());
                parseJsonFeed(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {

                }
            }
        }) {
            //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions.
            //Volley does retry for you if you have specified the policy.
            @Override
            public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                retryPolicy = new DefaultRetryPolicy(
                        TIMEOUT_MS,
                        2,
                        2.0F);
                return super.setRetryPolicy(retryPolicy);
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void parseJsonFeed(JSONArray response) {

        try {

            for (int i = 0; i < response.length(); i++) {
                JSONObject feedObj = (JSONObject) response.get(i);
//                feedTabNames.add(feedObj.getString("name"));

                JSONArray childrenJsonArray = feedObj.getJSONArray("childrens");
                ArrayList<FeedItemSubCategory> feedItemsSubcategories = null;
                for (int j = 0; j < childrenJsonArray.length(); j++) {
                    feedItemsSubcategories = new ArrayList<>();
                    JSONObject childObject = (JSONObject) childrenJsonArray.get(j);
                    FeedItemCategory feedItemCategory = new FeedItemCategory();
                    feedItemCategory.setCategoryName(childObject.getString("name"));
                    feedItemsCategory.add(feedItemCategory);
                    JSONArray products = childObject.getJSONArray("products");
                    for (int k = 0; k < products.length(); k++) {
                        JSONObject product = (JSONObject) products.get(k);
                        FeedItemSubCategory feedItemSubCategory = new FeedItemSubCategory();
                        feedItemSubCategory.setName(product.getString("name"));
                        JSONArray prices = product.getJSONArray("prices");
                        JSONObject price = (JSONObject) prices.get(0);
                        if (!price.isNull("amount") && !price.isNull("tax")) {
                            feedItemSubCategory.setPrice(price.getInt("amount") + price.getInt("tax"));
                        } else {
                            feedItemSubCategory.setPrice(-1);
                        }


                        if (!product.isNull("unit")) {
                            feedItemSubCategory.setUnit(product.getString("unit"));
                        } else {
                            feedItemSubCategory.setUnit(null);
                        }
                        if (!product.isNull("image")) {
                            feedItemSubCategory.setImage(BASE_URL + "files/" + product.getString("image"));
                        } else {
                            feedItemSubCategory.setImage(null);
                        }

                        feedItemSubCategory.setNumOfProds(0);

                        feedItemsSubcategories.add(feedItemSubCategory);
                    }
                    feedItemCategory.setChildren(feedItemsSubcategories);
                }

                addTab(feedObj.getString("name"), new ArrayList<FeedItemCategory>(feedItemsCategory));
                feedItemsCategory.clear();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void respond(String units, String price) {
        fragmentParent.changeTextInBottomToolbar(units, price);
    }


    public static void addItemToCart(FeedItemSubCategory cartItem) {
        cartItems.add(cartItem);
    }

    public static void removeItemFromCart(FeedItemSubCategory cartItem) {
        cartItems.remove(cartItem);
    }

    public static int numberOfTotalProductsInCart(){
        return cartItems.size();
    }

    public static int totalPriceInCart() {
        int totalPrice = 0;
        for(FeedItemSubCategory cartItem : cartItems){
            totalPrice+=cartItem.getPrice();
        }
        return totalPrice;
    }


}
