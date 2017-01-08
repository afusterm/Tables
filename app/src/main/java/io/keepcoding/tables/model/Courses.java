package io.keepcoding.tables.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Courses {
    private static List<Course> sCourses;

    public static Course get(int position) {
        return sCourses.get(position);
    }

    public static int size() {
        return sCourses.size();
    }

    public static void loadFromJSON(String json) throws JSONException {
        List<Course> courses = new ArrayList<>();

        JSONObject root = new JSONObject(json);
        JSONArray jsonCourses = root.getJSONArray("Courses");

        for (int i = 0; i < jsonCourses.length(); i++) {
            JSONObject jsonCourse = jsonCourses.getJSONObject(i);
            Course course = getCourseFromJSONObject(jsonCourse);
            courses.add(course);
        }

        sCourses = courses;
    }

    private static Course getCourseFromJSONObject(JSONObject jsonCourse) throws JSONException {
        String name = jsonCourse.getString("name");
        float price = (float) jsonCourse.getDouble("price");
        String pictureUrl = jsonCourse.getString("picture");
        JSONArray jsonAllergens = jsonCourse.getJSONArray("allergens");
        List<Allergen> allergens = getAllergensFromJSONArray(jsonAllergens);

        return new Course(name, price, pictureUrl, allergens);
    }

    private static List<Allergen> getAllergensFromJSONArray(JSONArray jsonAllergens) throws JSONException {
        List<Allergen> allergens = new ArrayList<>();

        for (int i = 0; i < jsonAllergens.length(); i++) {
            JSONObject jsonAllergen = jsonAllergens.getJSONObject(i);
            String name = jsonAllergen.getString("name");
            String icon = jsonAllergen.getString("icon");
            allergens.add(new Allergen(name, icon));
        }

        return allergens;
    }
}
