package io.keepcoding.tables.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import io.keepcoding.tables.activity.SettingsActivity;
import io.keepcoding.tables.model.Tables;

import static android.app.Activity.RESULT_OK;
import static io.keepcoding.tables.model.Tables.createTables;


public class TablesFragment extends Fragment {
    public interface Listener {
        void tableSelected(int position);
    }

    private static final int REQUEST_TABLES = 1;
    private static final String NUMBER_OF_TABLES = "NumberOfTables";

    private View root;
    private Listener mListener;

    public TablesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tables, container, false);

        int tables = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(NUMBER_OF_TABLES, 0);

        if (tables == 0) {
            openSettings();
        } else {
            createTables(tables);
            fillListWithTables(root);
        }

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
            openSettings();
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TABLES && resultCode == RESULT_OK) {
            int tables = data.getIntExtra(SettingsActivity.EXTRA_TABLES, 1);
            Tables.createTables(tables);
            fillListWithTables(root);
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putInt(NUMBER_OF_TABLES, tables)
                    .apply();
        }
    }

    public void setListener(final Listener listener) {
        mListener = listener;
    }

    private void openSettings() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);

        if (Tables.size() != 0) {
            intent.putExtra(SettingsActivity.EXTRA_TABLES, Tables.size());
        }

        startActivityForResult(intent, REQUEST_TABLES);
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
                if (mListener != null) {
                    mListener.tableSelected(position);
                }
            }
        });
    }

    private List<String> createTablesModel() {
        List<String> model = new ArrayList<>();

        for (int i = 0; i < Tables.size(); i++) {
            model.add(String.format(getString(R.string.table_item), i + 1));
        }

        return model;
    }
}
