package io.keepcoding.tables;

import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.tables.model.Allergen;
import io.keepcoding.tables.model.Plate;
import io.keepcoding.tables.model.RESTClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class PlateUnitTest {
    @Test
    public void downloadAndPopulateListOfPlates() throws Exception {
        RESTClient client = new FakeRESTClient();
        client.addGetListener(new RESTClient.GetListener() {
            public void getReceived(String json) {

                List<Plate> plates = null;
                try {
                    plates = Plate.getPlatesFromJSON(json);
                } catch (JSONException e) {
                    fail("The JSON is not correct");
                }

                Plate plate = plates.get(0);
                List<Allergen> allergens = plate.getAllergens();
                assertEquals("The name must be Tortellini carbonara", "Tortellini carbonara", plate.getName());
                assertEquals("The price must be 7.5", 7.5, plate.getPrice(), 0.1);
                assertEquals("The number of allergens is 2", 2, allergens.size());
                assertEquals("One allergen is Gluten", "Gluten", allergens.get(0).getName());

                plate = plates.get(1);
                allergens = plate.getAllergens();
                assertEquals("The name must be Entrecot de ternera", "Entrecot de ternera", plate.getName());
                assertEquals("The price must be 15", 15, plate.getPrice(), 0.1);
                assertEquals("The steak has no allergens", 0, allergens.size());
            }
        });

        client.get();
    }
}

class FakeRESTClient implements RESTClient {
    private List<GetListener> mGetListeners;

    public FakeRESTClient() {
        mGetListeners = new ArrayList<GetListener>();
    }

    @Override
    public void get() {
        String json = "{\n" +
                "\t\"Plates\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"number\": 1,\n" +
                "\t\t\t\"name\": \"Tortellini carbonara\",\n" +
                "\t\t\t\"description\": \"Tortellini con nata, bacon y champiñones\",\n" +
                "\t\t\t\"price\": 7.5,\n" +
                "\t\t\t\"allergens\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"name\": \"Gluten\",\n" +
                "\t\t\t\t\t\"icon\": \"url icono gluten\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"name\": \"Lacteos\",\n" +
                "\t\t\t\t\t\"icon\": \"url icono lacteos\"\t\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"number\": 2,\n" +
                "\t\t\t\"name\": \"Entrecot de ternera\",\n" +
                "\t\t\t\"description\": \"Carne de ternera\",\n" +
                "\t\t\t\"price\": 15,\n" +
                "\t\t\t\"allergens\": [\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
        for (GetListener gl: mGetListeners) {
            gl.getReceived(json);
        }
    }

    @Override
    public void addGetListener(GetListener getListener) {
        mGetListeners.add(getListener);
    }
}
