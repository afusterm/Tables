package io.keepcoding.tables.model;

import java.io.Serializable;

public class Allergen implements Serializable {
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
