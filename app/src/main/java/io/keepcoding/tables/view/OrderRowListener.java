package io.keepcoding.tables.view;

import io.keepcoding.tables.model.Order;

public interface OrderRowListener {
    void editButtonPushed(Order.Line line);
}
