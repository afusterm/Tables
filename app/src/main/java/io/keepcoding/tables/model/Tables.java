package io.keepcoding.tables.model;

import android.support.annotation.NonNull;

import java.util.List;


public class Tables {
    private final List<Table> mTables;

    public Tables(final @NonNull List<Table> tables) {
        mTables = tables;
    }

    public Table getTable(int number) {
        return mTables.get(number);
    }

    public int size() {
        return mTables.size();
    }
}
