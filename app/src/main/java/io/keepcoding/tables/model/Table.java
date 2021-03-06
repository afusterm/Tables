package io.keepcoding.tables.model;

import java.io.Serializable;

public class Table implements Serializable {
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
