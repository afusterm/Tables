package io.keepcoding.tables.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Order;


public class OrderRowViewHolder extends RecyclerView.ViewHolder {
    private TextView mCourseName;
    private EditText mUnits;
    private TextView mAmount;

    public OrderRowViewHolder(View itemView) {
        super(itemView);

        mCourseName = (TextView) itemView.findViewById(R.id.row_line_order_course);
        mUnits = (EditText) itemView.findViewById(R.id.row_line_order_units);
        mAmount = (TextView) itemView.findViewById(R.id.row_line_order_amount);
    }

    public void setLine(@NonNull final Order.Line line) {
        mCourseName.setText(line.getCourse().getName());
    }
}
