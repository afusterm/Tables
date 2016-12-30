package io.keepcoding.tables.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.keepcoding.tables.R;
import io.keepcoding.tables.fragment.TablesFragment;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Plate;

public class OrderActivity extends AppCompatActivity {
    private Order mOrder;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mOrder = (Order) getIntent().getSerializableExtra(TablesFragment.EXTRA_ORDER);
        mOrder.addListener(new Order.LinesListener() {
            @Override
            public void lineAdded(Order.Line line) {
                mAdapter.add(line.getPlate().getName());
            }

            @Override
            public void lineRemoved(int line) {

            }
        });

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
                mOrder.addLine(plate);
            }
        });
    }
}
