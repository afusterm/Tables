package io.keepcoding.tables.model;

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

    public String getIconURL() {
        return mIconURL;
    }
}
