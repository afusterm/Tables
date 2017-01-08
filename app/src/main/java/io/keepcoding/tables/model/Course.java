package io.keepcoding.tables.model;

import java.util.List;

public class Course {
    private String mName;
    private float mPrice;
    private String mPictureURL;
    private List<Allergen> mAllergens;

    public Course(String name, float price, String pictureURL, List<Allergen> allergens) {
        mName = name;
        mPrice = price;
        mPictureURL = pictureURL;
        mAllergens = allergens;
    }

    public String getName() {
        return mName;
    }

    public float getPrice() {
        return mPrice;
    }

    public List<Allergen> getAllergens() {
        return mAllergens;
    }

    public String getPictureURL() {
        return mPictureURL;
    }
}
