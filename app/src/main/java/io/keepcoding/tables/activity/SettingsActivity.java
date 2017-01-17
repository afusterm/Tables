package io.keepcoding.tables.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

import io.keepcoding.tables.R;


public class SettingsActivity extends AppCompatActivity {
    public static final String EXTRA_TABLES = "tables";

    private NumberPicker mPicker;
    private int mTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mTables = getIntent().getIntExtra(EXTRA_TABLES, 1);
        configurePicker();

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void configurePicker() {
        mPicker = (NumberPicker) findViewById(R.id.tables_picker);
        mPicker.setMaxValue(100);
        mPicker.setMinValue(1);
        mPicker.setWrapSelectorWheel(true);
        mPicker.setValue(mTables);
    }

    private void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void save() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TABLES, mPicker.getValue());
        setResult(RESULT_OK, intent);
        finish();
    }
}
