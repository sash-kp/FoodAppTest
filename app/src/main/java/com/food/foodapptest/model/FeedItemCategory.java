package com.food.foodapptest.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sashwat on 8/10/2015.
 */
public class FeedItemCategory implements Serializable{
    private String categoryName,tabName;

    // ArrayList to store child objects
    private ArrayList<FeedItemSubCategory> children;

    public FeedItemCategory() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



    // ArrayList to store child objects
    public ArrayList<FeedItemSubCategory> getChildren()
    {
        return children;
    }

    public void setChildren(ArrayList<FeedItemSubCategory> children)
    {
        this.children = children;
    }
}
