package io.keepcoding.tables.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 07/12/2016.
 */

public class Tables {
    private static List<Table> mTables = new ArrayList<Table>();

    private Tables() {
    }

    public static void createTable() {
        int number = mTables.size() + 1;
        mTables.add(new Table(number));
    }

    public static Table getTable(int number) {
        return mTables.get(number);
    }

    public static int size() {
        return mTables.size();
    }
}
