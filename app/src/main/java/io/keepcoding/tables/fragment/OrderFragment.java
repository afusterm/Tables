package io.keepcoding.tables.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.tables.R;
import io.keepcoding.tables.activity.CoursesActivity;
import io.keepcoding.tables.activity.EditCourseActivity;
import io.keepcoding.tables.adapter.OrdersAdapter;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Tables;
import io.keepcoding.tables.view.OrderRowListener;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    public static final String EXTRA_COURSE_NAME = "OrderRowViewHolder.EXTRA_COURSE_NAME";
    public static final String EXTRA_VARIANT = "OrderRowViewHolder.EXTRA_VARIANT";
    public static final String EXTRA_LINE_NUMBER = "OrderRowViewHolder.LINE_NUMBER";

    private static final int ORDER_REQUEST = 1;
    private static final int EDIT_REQUEST = 2;

    private Order mOrder;
    private RecyclerView mOrdersRecyclerView;

    public OrderFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        mOrdersRecyclerView = (RecyclerView) root.findViewById(R.id.orders_recycler_view);
        mOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int position = getActivity().getIntent().getIntExtra(TablesFragment.EXTRA_ORDER, 0);
        setOrder(Tables.get(position).getOrder());

        configureAddButton(root);


        return root;
    }

    public void setOrder(@NonNull final Order order) {
        mOrder = order;

        final OrdersAdapter adapter = new OrdersAdapter(getActivity(), mOrder);
        mOrdersRecyclerView.setAdapter(adapter);

        adapter.setListener(new OrderRowListener() {
            @Override
            public void editButtonPushed(Order.Line line) {
                Intent i = new Intent(getActivity(), EditCourseActivity.class);
                i.putExtra(EXTRA_COURSE_NAME, line.getCourse().getName());
                i.putExtra(EXTRA_VARIANT, line.getVariant());
                i.putExtra(EXTRA_LINE_NUMBER, line.getNumber());
                startActivityForResult(i, EDIT_REQUEST);
            }
        });

        mOrder.addListener(new Order.OrderListener() {
            @Override
            public void lineAdded(Order.Line line) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void lineRemoved(int line) {

            }
        });
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
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            int lineNumber = data.getIntExtra(OrderFragment.EXTRA_LINE_NUMBER, 0);
            String variant = data.getStringExtra(OrderFragment.EXTRA_VARIANT);
            Order.Line line = mOrder.getLine(lineNumber);
            line.setVariant(variant);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_order, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_order_bill) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle(R.string.menu_order_bill);
            dialog.setMessage(String.format(getString(R.string.order_fragment_total_bill_text), mOrder.calculateTotal()));
            dialog.setNeutralButton(R.string.order_ok_text, null);

            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
