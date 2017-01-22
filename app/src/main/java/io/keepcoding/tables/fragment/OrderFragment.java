package io.keepcoding.tables.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
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
import io.keepcoding.tables.activity.TablesActivity;
import io.keepcoding.tables.adapter.OrdersAdapter;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Tables;
import io.keepcoding.tables.view.OrderRowListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    public interface OnAddOrderClickedListener {
        void addOrderButtonClicked();
    }

    public interface OnEditOrderClickedListener {
        void editOrderClicked(final Order.Line line);
    }

    public static final String EXTRA_COURSE_NAME = "OrderRowViewHolder.EXTRA_COURSE_NAME";
    public static final String EXTRA_VARIANT = "OrderRowViewHolder.EXTRA_VARIANT";
    public static final String EXTRA_LINE_NUMBER = "OrderRowViewHolder.LINE_NUMBER";
    public static final int ORDER_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;

    private Order mOrder;
    private RecyclerView mOrdersRecyclerView;
    private OnAddOrderClickedListener mOnAddOrderClickedListener;
    private OnEditOrderClickedListener mOnEditOrderClickedListener;

    public void setOnAddOrderClickedListener(OnAddOrderClickedListener onAddOrderClickedListener) {
        mOnAddOrderClickedListener = onAddOrderClickedListener;
    }

    public void setOnEditOrderClickedListener(OnEditOrderClickedListener onEditOrderClickedListener) {
        mOnEditOrderClickedListener = onEditOrderClickedListener;
    }

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

        int position = getActivity().getIntent().getIntExtra(TablesActivity.EXTRA_ORDER, 0);
        setOrder(Tables.get(position).getOrder());

        configureAddButton(root);


        return root;
    }

    public void setOrder(@NonNull final Order order) {
        mOrder = order;

        final OrdersAdapter adapter = new OrdersAdapter(mOrder);
        mOrdersRecyclerView.setAdapter(adapter);

        adapter.setListener(new OrderRowListener() {
            @Override
            public void editButtonPushed(Order.Line line) {
                if (mOnEditOrderClickedListener != null) {
                    mOnEditOrderClickedListener.editOrderClicked(line);
                }
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
                if (mOnAddOrderClickedListener != null) {
                    mOnAddOrderClickedListener.addOrderButtonClicked();
                }
            }
        });
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
