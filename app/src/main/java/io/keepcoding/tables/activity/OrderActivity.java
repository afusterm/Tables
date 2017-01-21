package io.keepcoding.tables.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import io.keepcoding.tables.R;
import io.keepcoding.tables.fragment.OrderFragment;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Tables;

import static io.keepcoding.tables.fragment.OrderFragment.EDIT_REQUEST;

public class OrderActivity extends AppCompatActivity {
    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        int tableNumber = getIntent().getIntExtra(TablesActivity.EXTRA_ORDER, 0);
        mOrder = Tables.get(tableNumber).getOrder();

        FragmentManager fm = getFragmentManager();
        OrderFragment orderFragment = (OrderFragment) fm.findFragmentById(R.id.activity_order_fragment_order);
        if (orderFragment != null) {
            orderFragment.setOnAddOrderClickedListener(new OrderFragment.OnAddOrderClickedListener() {
                @Override
                public void addOrderButtonClicked() {
                    Intent intent = new Intent(OrderActivity.this, CoursesActivity.class);
                    startActivityForResult(intent, OrderFragment.ORDER_REQUEST);
                }
            });

            orderFragment.setOnEditOrderClickedListener(new OrderFragment.OnEditOrderClickedListener() {
                @Override
                public void editOrderClicked(Order.Line line) {
                    Intent i = new Intent(OrderActivity.this, EditCourseActivity.class);
                    i.putExtra(OrderFragment.EXTRA_COURSE_NAME, line.getCourse().getName());
                    i.putExtra(OrderFragment.EXTRA_VARIANT, line.getVariant());
                    i.putExtra(OrderFragment.EXTRA_LINE_NUMBER, line.getNumber());
                    startActivityForResult(i, EDIT_REQUEST);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OrderFragment.ORDER_REQUEST && resultCode == RESULT_OK) {
            int position = data.getIntExtra(CoursesActivity.EXTRA_COURSE_POSITION, 0);
            mOrder.addLine(Courses.get(position));
        } else if (requestCode == OrderFragment.EDIT_REQUEST && resultCode == RESULT_OK) {
            int lineNumber = data.getIntExtra(OrderFragment.EXTRA_LINE_NUMBER, 0);
            String variant = data.getStringExtra(OrderFragment.EXTRA_VARIANT);
            Order.Line line = mOrder.getLine(lineNumber);
            line.setVariant(variant);
        }
    }
}
