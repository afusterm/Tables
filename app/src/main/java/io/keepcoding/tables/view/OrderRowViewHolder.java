package io.keepcoding.tables.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Order;


public class OrderRowViewHolder extends RecyclerView.ViewHolder {
    private TextView mCourseName;
    private EditText mUnits;
    private TextView mAmount;
    private ImageButton mAddButton;
    private ImageButton mDecrementButton;
    private ImageButton mOrderEditButton;
    private Order.Line mLine;
    private OrderRowListener mListener;

    public OrderRowViewHolder(View itemView) {
        super(itemView);

        mCourseName = (TextView) itemView.findViewById(R.id.row_line_order_course);
        mUnits = (EditText) itemView.findViewById(R.id.row_line_order_units);
        mAmount = (TextView) itemView.findViewById(R.id.row_line_order_amount);
        mAddButton = (ImageButton) itemView.findViewById(R.id.row_line_order_add_button);
        mDecrementButton = (ImageButton) itemView.findViewById(R.id.row_line_order_decrement_button);
        mOrderEditButton = (ImageButton) itemView.findViewById(R.id.row_line_order_edit);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int units = mLine.getUnits() + 1;
                updateUnits(units);
            }
        });

        mDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int units = mLine.getUnits();
                if (units > 1) {
                    units--;
                    updateUnits(units);
                }
            }
        });

        mOrderEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.editButtonPushed(mLine);
                }
            }
        });
    }

    private void updateUnits(int units) {
        mLine.setUnits(units);
        mUnits.setText(String.valueOf(units));
        float amount = mLine.getCourse().getPrice() * units;
        mAmount.setText(String.valueOf(amount));
    }

    public void setLine(@NonNull final Order.Line line) {
        mLine = line;
        mCourseName.setText(line.getCourse().getName());
        mAmount.setText(String.valueOf(line.getCourse().getPrice() * line.getUnits()));
    }

    public void setListener(OrderRowListener listener) {
        mListener = listener;
    }
}
