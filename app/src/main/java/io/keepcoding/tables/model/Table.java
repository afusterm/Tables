package io.keepcoding.tables.model;

public class Table {
    private int mNumber;
    private Order mOrder;

    public Table(int number) {
        mNumber = number;
        mOrder = new Order(this);
    }

    public int getNumber() {
        return mNumber;
    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
    }
}
