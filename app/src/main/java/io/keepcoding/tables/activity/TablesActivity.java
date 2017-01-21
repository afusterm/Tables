package io.keepcoding.tables.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import io.keepcoding.tables.R;
import io.keepcoding.tables.fragment.OrderFragment;
import io.keepcoding.tables.fragment.TablesFragment;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Table;
import io.keepcoding.tables.model.Tables;

public class TablesActivity extends AppCompatActivity {
    public static final String EXTRA_ORDER = "io.keepcoding.tables.fragment.TablesActivity.EXTRA_ORDER";

    private static final String COURSES_JSON_URL = "https://raw.githubusercontent.com/afusterm/tables_resources/master/courses.json";
    private static final String TAG = TablesActivity.class.getCanonicalName();
    private static final String TABLE_NUMBER = "TABLE_NUMBER";
    private static final String TABLES_DATA = "TABLES_DATA";

    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupThreadPolicy();

        setContentView(R.layout.activity_tables);

        if (Courses.size() == 0) {
            downloadCourses();
        }

        if (savedInstanceState != null) {
            int tableNumber = savedInstanceState.getInt(TABLE_NUMBER);
            ArrayList<Table> tables = (ArrayList<Table>) savedInstanceState.getSerializable(TABLES_DATA);
            Tables.createTables(tables);
            mOrder = Tables.get(tableNumber - 1).getOrder();
        }

        FragmentManager fm = getFragmentManager();
        TablesFragment tablesFragment = (TablesFragment) fm.findFragmentById(R.id.activity_tables_fragment_tables);
        final OrderFragment orderFragment = (OrderFragment) fm.findFragmentById(R.id.activity_order_fragment_order);

        if (orderFragment == null) {
            tablesFragment.setListener(new TablesFragment.Listener() {
                @Override
                public void tableSelected(int position) {
                    mOrder = Tables.get(position).getOrder();
                    Intent intent = new Intent(TablesActivity.this, OrderActivity.class);
                    intent.putExtra(EXTRA_ORDER, position);
                    startActivity(intent);
                }
            });
        } else {
            orderFragment.setOnAddOrderClickedListener(new OrderFragment.OnAddOrderClickedListener() {
                @Override
                public void addOrderButtonClicked() {
                    Intent intent = new Intent(TablesActivity.this, CoursesActivity.class);
                    startActivityForResult(intent, OrderFragment.ORDER_REQUEST);
                }
            });

            orderFragment.setOnEditOrderClickedListener(new OrderFragment.OnEditOrderClickedListener() {
                @Override
                public void editOrderClicked(Order.Line line) {
                    Intent i = new Intent(TablesActivity.this, EditCourseActivity.class);
                    i.putExtra(OrderFragment.EXTRA_COURSE_NAME, line.getCourse().getName());
                    i.putExtra(OrderFragment.EXTRA_VARIANT, line.getVariant());
                    i.putExtra(OrderFragment.EXTRA_LINE_NUMBER, line.getNumber());
                    startActivityForResult(i, OrderFragment.EDIT_REQUEST);
                }
            });

            tablesFragment.setListener(new TablesFragment.Listener() {
                @Override
                public void tableSelected(int position) {
                    mOrder = Tables.get(position).getOrder();
                    orderFragment.setOrder(mOrder);
                }
            });
        }
    }

    private void setupThreadPolicy() {
        // this is for avoid the android.os.StrictMode$AndroidBlockGuardPolicy.onNetwork exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mOrder != null) {
            outState.putInt(TABLE_NUMBER, mOrder.getTable().getNumber());
        } else {
            outState.putInt(TABLE_NUMBER, 1);
        }

        outState.putSerializable(TABLES_DATA, Tables.allTables());
    }

    private void downloadCourses() {
        AsyncTask<Void, Void, String> coursesDownloader = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Snackbar.make(findViewById(android.R.id.content), "Descargando platos", Snackbar.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                StringBuilder sb = new StringBuilder();

                try {
                    URLConnection connection = new URL(COURSES_JSON_URL).openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    reader.close();

                    return sb.toString();
                } catch (IOException e) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TablesActivity.this);
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Se produjo un error al descargar los platos");
                    alertDialog.show();
                }

                return sb.toString();
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                loadCourses(json);
            }
        };

        coursesDownloader.execute();
    }

    private void loadCourses(String json) {
        try {
            Courses.loadFromJSON(json);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            Snackbar.make(findViewById(android.R.id.content), R.string.tables_activity_error_load,
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_order_bill && mOrder != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.menu_order_bill);
            dialog.setMessage(String.format(getString(R.string.order_fragment_total_bill_text), mOrder.calculateTotal()));
            dialog.setNeutralButton(R.string.order_ok_text, null);

            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
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
