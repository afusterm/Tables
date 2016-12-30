package io.keepcoding.tables.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Plate;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private Order mOrder;
    private ArrayAdapter<String> mAdapter;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        mOrder = (Order) getActivity().getIntent().getSerializableExtra(TablesFragment.EXTRA_ORDER);
        mOrder.addListener(new Order.LinesListener() {
            @Override
            public void lineAdded(Order.Line line) {
                mAdapter.add(line.getPlate().getName());
            }

            @Override
            public void lineRemoved(int line) {

            }
        });

        ListView listView = (ListView) root.findViewById(android.R.id.list);
        mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(mAdapter);

        configureAddButton(root);

        return root;
    }

    private void configureAddButton(View root){
        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plate plate = new Plate(1, "Plato 1", "Descripcion plato 1", 5f, null);
                mOrder.addLine(plate);
            }
        });
    }
}
