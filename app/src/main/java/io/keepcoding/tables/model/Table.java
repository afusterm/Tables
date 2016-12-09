package io.keepcoding.tables.model;

public class Table {
    private int mNumber;
    private Order mOrder;

    Table(int number) {
        mNumber = number;
        mOrder = new Order();
    }

    public int getNumber() {
        return mNumber;
    }

    public Order getOrder() {
        return mOrder;
    }
}
