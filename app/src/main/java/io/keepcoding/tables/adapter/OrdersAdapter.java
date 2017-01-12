package io.keepcoding.tables.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.view.OrderRowViewHolder;

public class OrdersAdapter extends RecyclerView.Adapter<OrderRowViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Order mOrder;

    public OrdersAdapter(@NonNull final Context context, @NonNull final Order order) {
        mLayoutInflater = LayoutInflater.from(context);
        mOrder = order;
        mOrder.addListener(new Order.LinesListener() {
            @Override
            public void lineAdded(Order.Line line) {

            }

            @Override
            public void lineRemoved(int line) {

            }
        });
    }

    @Override
    public OrderRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = mLayoutInflater.inflate(R.layout.row_line_order, parent, false);

        return new OrderRowViewHolder(root);
    }

    @Override
    public void onBindViewHolder(OrderRowViewHolder holder, int position) {
        final Order.Line line = mOrder.getLine(position);
        holder.setLine(line);
    }

    @Override
    public int getItemCount() {
        return mOrder.size();
    }
}
