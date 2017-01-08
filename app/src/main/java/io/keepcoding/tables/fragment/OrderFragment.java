package io.keepcoding.tables.fragment;


import android.content.Intent;
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
import io.keepcoding.tables.activity.CoursesActivity;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.model.Order;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private static final int ORDER_REQUEST = 1;

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
                mAdapter.add(line.getCourse().getName());
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
                Intent intent = new Intent(getActivity(), CoursesActivity.class);
                startActivityForResult(intent, ORDER_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ORDER_REQUEST && resultCode == RESULT_OK) {
            int position = data.getIntExtra(CoursesActivity.EXTRA_COURSE_POSITION, 0);
            mOrder.addLine(Courses.get(position));
        }
    }
}
