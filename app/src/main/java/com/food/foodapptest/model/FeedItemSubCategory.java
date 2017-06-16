package com.food.foodapptest.model;


import java.io.Serializable;

public class FeedItemSubCategory implements Serializable{

    private int price,numOfProds;
    private String name, image,unit;

    public FeedItemSubCategory() {
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumOfProds() {
        return numOfProds;
    }

    public void setNumOfProds(int numOfProds) {
        this.numOfProds = numOfProds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        return getClass() == obj.getClass();
    }

}