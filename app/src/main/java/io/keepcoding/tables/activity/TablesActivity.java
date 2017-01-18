package io.keepcoding.tables.activity;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Courses;

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
        AsyncTask<Void, Void, String> coursesDownloader = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Snackbar.make(findViewById(android.R.id.content), "Descargando platos", Snackbar.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                StringBuilder sb = new StringBuilder();

                try {
                    URLConnection connection = new URL(COURSES_JSON_URL).openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    reader.close();

                    return sb.toString();
                } catch (IOException e) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TablesActivity.this);
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Se produjo un error al descargar los platos");
                    alertDialog.show();
                }

                return sb.toString();
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                loadCourses(json);
            }
        };

        coursesDownloader.execute();
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
