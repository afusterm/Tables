package io.keepcoding.tables.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.tables.R;

public class TablesActivity extends Activity {
    private static final int REQUEST_TABLES = 1;
    private static final String NUMBER_OF_TABLES = "NumberOfTables";

    private ListView mListView;
    private MenuItem mSettings;
    private int mTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        int tables = PreferenceManager.getDefaultSharedPreferences(this).getInt(NUMBER_OF_TABLES, 0);

        if (tables == 0) {
            openSettings();
        } else {
            setTables(tables);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tables, menu);

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);

        if (item.getItemId() == R.id.menu_show_settings) {
            openSettings();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TABLES && resultCode == RESULT_OK) {
            setTables(data.getIntExtra(SettingsActivity.EXTRA_TABLES, 1));
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putInt(NUMBER_OF_TABLES, mTables)
                    .apply();
        }
    }

    private void setTables(int tables) {
        mTables = tables;
        fillListWithTables();
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);

        if (mTables != 0) {
            intent.putExtra(SettingsActivity.EXTRA_TABLES, mTables);
        }

        startActivityForResult(intent, REQUEST_TABLES);
    }

    private void fillListWithTables() {
        ListView list = (ListView) findViewById(android.R.id.list);
        List<String> tables = createTablesModel(mTables);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tables);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
    }

    private List<String> createTablesModel(int tables) {
        List<String> model = new ArrayList<String>();

        for (int i = 0; i < tables; i++) {
            model.add(String.format("Mesa %d", i + 1));
        }

        return model;
    }
}
