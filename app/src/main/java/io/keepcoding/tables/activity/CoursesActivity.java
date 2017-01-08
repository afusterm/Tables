package io.keepcoding.tables.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.keepcoding.tables.R;
import io.keepcoding.tables.adapter.CoursesAdapter;
import io.keepcoding.tables.view.OnElementClick;

public class CoursesActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_POSITION = "COURSE_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        RecyclerView coursesRecyclerView = (RecyclerView) findViewById(R.id.courses_recycler_view);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CoursesAdapter adapter = new CoursesAdapter(this);
        adapter.setOnElementClickListener(new OnElementClick() {
            @Override
            public void clickedOn(int position) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_COURSE_POSITION, position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        coursesRecyclerView.setAdapter(adapter);
    }
}
