package io.keepcoding.tables.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.tables.R;
import io.keepcoding.tables.activity.OrderActivity;
import io.keepcoding.tables.activity.SettingsActivity;
import io.keepcoding.tables.model.Table;
import io.keepcoding.tables.model.Tables;

import static android.app.Activity.RESULT_OK;


public class TablesFragment extends Fragment {
    public static final String EXTRA_ORDER = "io.keepcoding.tables.fragment.TablesActivity.EXTRA_ORDER";

    private static final int REQUEST_TABLES = 1;
    private static final String NUMBER_OF_TABLES = "NumberOfTables";

    private Tables mTables;


    public TablesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tables, container, false);

        int tables = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(NUMBER_OF_TABLES, 0);

        if (tables == 0) {
            openSettings(0);
        } else {
            createTables(tables);
            fillListWithTables(root);
        }

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_tables, menu);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TABLES && resultCode == RESULT_OK) {
            int tables = data.getIntExtra(SettingsActivity.EXTRA_TABLES, 1);
            createTables(tables);
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putInt(NUMBER_OF_TABLES, tables)
                    .apply();
        }
    }

    private void openSettings(int numberOfTables) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);

        if (numberOfTables != 0) {
            intent.putExtra(SettingsActivity.EXTRA_TABLES, numberOfTables);
        }

        startActivityForResult(intent, REQUEST_TABLES);
    }

    private void createTables(int numberOfTables) {
        List<Table> tables = new ArrayList<>();
        for (int i = 0; i < numberOfTables; i++) {
            tables.add(new Table(i));
        }

        mTables = new Tables(tables);
    }

    private void fillListWithTables(View view) {
        ListView list = (ListView) view.findViewById(android.R.id.list);
        List<String> tables = createTablesModel();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, tables);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
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