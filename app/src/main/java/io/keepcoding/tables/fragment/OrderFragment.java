package io.keepcoding.tables.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.tables.R;
import io.keepcoding.tables.activity.CoursesActivity;
import io.keepcoding.tables.adapter.OrdersAdapter;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.model.Order;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private static final int ORDER_REQUEST = 1;

    private Order mOrder;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        mOrder = (Order) getActivity().getIntent().getSerializableExtra(TablesFragment.EXTRA_ORDER);

        RecyclerView ordersRecyclerView = (RecyclerView) root.findViewById(R.id.orders_recycler_view);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final OrdersAdapter adapter = new OrdersAdapter(getActivity(), mOrder);
        ordersRecyclerView.setAdapter(adapter);

        mOrder.addListener(new Order.LinesListener() {
            @Override
            public void lineAdded(Order.Line line) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void lineRemoved(int line) {

            }
        });


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
