package io.keepcoding.tables.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.keepcoding.tables.R;
import io.keepcoding.tables.fragment.OrderFragment;

public class EditCourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        String courseName = getIntent().getStringExtra(OrderFragment.EXTRA_COURSE_NAME);
        String variant = getIntent().getStringExtra(OrderFragment.EXTRA_VARIANT);
        final int lineNumber = getIntent().getIntExtra(OrderFragment.EXTRA_LINE_NUMBER, 0);

        TextView courseNameTextView = (TextView) findViewById(R.id.activity_edit_course_name);
        final EditText variantEditText = (EditText) findViewById(R.id.activity_edit_course_variant);
        Button saveButton = (Button) findViewById(R.id.activity_edit_course_save_button);

        courseNameTextView.setText(courseName);
        variantEditText.setText(variant);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra(OrderFragment.EXTRA_LINE_NUMBER, lineNumber);
                i.putExtra(OrderFragment.EXTRA_VARIANT, variantEditText.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
