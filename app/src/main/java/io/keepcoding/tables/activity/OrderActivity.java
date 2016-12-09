package io.keepcoding.tables.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Plate;
import io.keepcoding.tables.model.Tables;

public class OrderActivity extends Activity {
    private Order mOrder;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        int table = getIntent().getIntExtra(TablesActivity.EXTRA_TABLE_NUMBER, 0);
        configureOrder(table);

        ListView listView = (ListView) findViewById(android.R.id.list);
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(mAdapter);

        configureAddButton();
    }

    private void configureAddButton(){
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plate plate = new Plate(1, "Plato 1", "Descripcion plato 1", 5f, null);
                mOrder.addLine(plate, null);
            }
        });
    }

    private void configureOrder(int table) {
        mOrder = Tables.getTable(table).getOrder();
        mOrder.addListener(new Order.LinesListener() {
            @Override
            public void lineAdded(Order.Line line) {
                mAdapter.add(line.getPlate().getName());
            }

            @Override
            public void lineRemoved(Order.Line line) {

            }
        });
    }
}
