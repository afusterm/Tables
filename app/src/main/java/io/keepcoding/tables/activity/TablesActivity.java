package io.keepcoding.tables.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
import io.keepcoding.tables.model.Table;
import io.keepcoding.tables.model.Tables;

public class TablesActivity extends AppCompatActivity {
    public static final String EXTRA_ORDER = "io.keepcoding.tables.activity.TablesActivity.EXTRA_ORDER";

    private static final int REQUEST_TABLES = 1;
    private static final String NUMBER_OF_TABLES = "NumberOfTables";

    private ListView mListView;
    private MenuItem mSettings;
    private Tables mTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        int tables = PreferenceManager.getDefaultSharedPreferences(this).getInt(NUMBER_OF_TABLES, 0);

        if (tables == 0) {
            openSettings(0);
        } else {
            createTables(tables);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_show_settings) {
            openSettings(mTables.size());
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TABLES && resultCode == RESULT_OK) {
            int tables = data.getIntExtra(SettingsActivity.EXTRA_TABLES, 1);
            createTables(tables);
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putInt(NUMBER_OF_TABLES, tables)
                    .apply();
        }
    }

    private void createTables(int numberOfTables) {
        List<Table> tables = new ArrayList<>();
        for (int i = 0; i < numberOfTables; i++) {
            tables.add(new Table(i));
        }

        mTables = new Tables(tables);

        fillListWithTables();
    }

    private void openSettings(int numberOfTables) {
        Intent intent = new Intent(this, SettingsActivity.class);

        if (numberOfTables != 0) {
            intent.putExtra(SettingsActivity.EXTRA_TABLES, numberOfTables);
        }

        startActivityForResult(intent, REQUEST_TABLES);
    }

    private void fillListWithTables() {
        ListView list = (ListView) findViewById(android.R.id.list);
        List<String> tables = createTablesModel();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tables);
        list.setAdapter(adapter);
        final Context context = this;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(context, OrderActivity.class);
                final Table table = mTables.getTable(position);
                intent.putExtra(EXTRA_ORDER, table.getOrder());
                startActivity(intent);
            }
        });
    }

    private List<String> createTablesModel() {
        List<String> model = new ArrayList<>();

        for (int i = 0; i < mTables.size(); i++) {
            model.add(String.format(getString(R.string.table_item), i + 1));
        }

        return model;
    }
}
