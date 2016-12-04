package io.keepcoding.tables.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 03/12/2016.
 */

public class Plate {
    private int mNumber;
    private String mName;
    private String mDescription;
    private float mPrice;
    private List<Allergen> mAllergens;

    private final static String TAG = Plate.class.getCanonicalName();

    public static List<Plate> getPlatesFromJSON(String json) throws JSONException {
        List<Plate> plates = new ArrayList<Plate>();

        JSONObject root = new JSONObject(json);
        JSONArray jsonPlates = root.getJSONArray("Plates");

        for (int i = 0; i < jsonPlates.length(); i++) {
            JSONObject jsonPlate = jsonPlates.getJSONObject(i);
            Plate plate = getPlateFromJSONObject(jsonPlate);
            plates.add(plate);
        }

        return plates;
    }

    private static Plate getPlateFromJSONObject(JSONObject jsonPlate) throws JSONException {
        int number = jsonPlate.getInt("number");
        String name = jsonPlate.getString("name");
        String description = jsonPlate.getString("description");
        float price = (float) jsonPlate.getDouble("price");
        JSONArray jsonAllergens = jsonPlate.getJSONArray("allergens");
        List<Allergen> allergens = getAllergensFromJSONArray(jsonAllergens);

        return new Plate(number, name, description, price, allergens);
    }

    private static List<Allergen> getAllergensFromJSONArray(JSONArray jsonAllergens) throws JSONException {
        List<Allergen> allergens = new ArrayList<Allergen>();

        for (int i = 0; i < jsonAllergens.length(); i++) {
            JSONObject jsonAllergen = jsonAllergens.getJSONObject(i);
            String name = jsonAllergen.getString("name");
            String icon = jsonAllergen.getString("icon");
            allergens.add(new Allergen(name, icon));
        }

        return allergens;
    }

    public Plate(int number, String name, String description, float price, List<Allergen> allergens) {
        mNumber = number;
        mName = name;
        mDescription = description;
        mPrice = price;
        mAllergens = allergens;
    }

    public int getNumber() {
        return mNumber;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public float getPrice() {
        return mPrice;
    }

    public List<Allergen> getAllergens() {
        return mAllergens;
    }
}
