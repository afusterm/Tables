package io.keepcoding.tables.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.net.CoursesRESTClient;
import io.keepcoding.tables.net.RESTClient;

public class TablesActivity extends AppCompatActivity {
    private static final String COURSES_JSON_URL = "https://raw.githubusercontent.com/afusterm/tables_resources/master/courses.json";
    private static final String TAG = TablesActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupThreadPolicy();
        downloadCourses();
        setContentView(R.layout.activity_tables);
    }

    private void setupThreadPolicy() {
        // this is for avoid the android.os.StrictMode$AndroidBlockGuardPolicy.onNetwork exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void downloadCourses() {
        Thread coursesDownloader = new Thread(new Runnable() {
            @Override
            public void run() {
                RESTClient coursesClient = createCoursesClient();
                coursesClient.get();
            }
        });

        coursesDownloader.start();
    }

    @NonNull
    private RESTClient createCoursesClient() {
        RESTClient coursesClient = new CoursesRESTClient(COURSES_JSON_URL);
        coursesClient.setGetListener(new RESTClient.GetListener() {
            @Override
            public void getReceived(String json) {
                loadCourses(json);
            }

            public void errorOnDownload() {
                Snackbar.make(findViewById(android.R.id.content), R.string.tables_activity_error_download,
                        Snackbar.LENGTH_LONG).show();
            }
        });

        return coursesClient;
    }

    private void loadCourses(String json) {
        try {
            Courses.loadFromJSON(json);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            Snackbar.make(findViewById(android.R.id.content), R.string.tables_activity_error_load,
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
