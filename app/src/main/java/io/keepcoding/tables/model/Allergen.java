package io.keepcoding.tables.model;

/**
 * Created by alejandro on 03/12/2016.
 */

public class Allergen {
    private String mName;
    private String mIconURL;

    public Allergen(String name, String iconURL) {
        mName = name;
        mIconURL = iconURL;
    }

    public String getName() {
        return mName;
    }

    public String iconURL() {
        return mIconURL;
    }
}
