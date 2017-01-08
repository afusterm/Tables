package io.keepcoding.tables.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.keepcoding.tables.R;
import io.keepcoding.tables.adapter.CoursesAdapter;

public class CoursesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        RecyclerView coursesRecyclerView = (RecyclerView) findViewById(R.id.courses_recycler_view);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CoursesAdapter adapter = new CoursesAdapter(this);
        coursesRecyclerView.setAdapter(adapter);
    }
}
