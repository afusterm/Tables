package io.keepcoding.tables;

import org.json.JSONException;
import org.junit.Test;

import java.util.List;

import io.keepcoding.tables.model.Allergen;
import io.keepcoding.tables.model.Course;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.net.RESTClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class CourseUnitTest {
    @Test
    public void downloadAndPopulateListOfCourses() throws Exception {
        RESTClient client = new FakeRESTClient();
        client.setGetListener(new RESTClient.GetListener() {
            public void getReceived(String json) {
                try {
                    Courses.loadFromJSON(json);
                } catch (JSONException e) {
                    fail("The JSON is not correct");
                }

                Course course = Courses.get(0);
                List<Allergen> allergens = course.getAllergens();
                assertEquals("The name must be Tortellini carbonara", "Tortellini carbonara", course.getName());
                assertEquals("The price must be 7.5", 7.5, course.getPrice(), 0.1);
                assertEquals("The number of allergens is 2", 2, allergens.size());
                assertEquals("One allergen is Gluten", "Gluten", allergens.get(0).getName());

                course = Courses.get(1);
                allergens = course.getAllergens();
                assertEquals("The name must be Entrecot de ternera", "Entrecot de ternera", course.getName());
                assertEquals("The price must be 15", 15, course.getPrice(), 0.1);
                assertEquals("The steak has no allergens", 0, allergens.size());
            }

            @Override
            public void errorOnDownload() {
            }
        });

        client.get();
    }
}

class FakeRESTClient implements RESTClient {
    private GetListener mGetListener;

    FakeRESTClient() {
    }

    @Override
    public void get() {
        String json = "{\n" +
                "\t\"Courses\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Tortellini carbonara\",\n" +
                "\t\t\t\"price\": 7.5,\n" +
                "\t\t\t\"picture\": \"picture url\",\n" +
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
                "\t\t\t\"name\": \"Entrecot de ternera\",\n" +
                "\t\t\t\"price\": 15,\n" +
                "\t\t\t\"picture\": \"picture url\",\n" +
                "\t\t\t\"allergens\": [\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
        if (mGetListener != null) {
            mGetListener.getReceived(json);
        }
    }

    @Override
    public void setGetListener(GetListener getListener) {
        mGetListener = getListener;
    }
}
