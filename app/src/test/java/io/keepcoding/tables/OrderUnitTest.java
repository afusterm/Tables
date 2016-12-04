package io.keepcoding.tables;

import org.junit.Test;

import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Plate;

import static org.junit.Assert.assertEquals;

/**
 * Created by alejandro on 04/12/2016.
 */

public class OrderUnitTest {
    @Test
    public void addOneLineAndGetNoticed() {
        Order order = new Order(1);
        order.addListener(new Order.LinesListener() {
            public void lineAdded(int line) {
                assertEquals("The line must be 0", 0, line);
            }

            @Override
            public void lineRemoved(int line) {

            }
        });
    }

    @Test
    public void removeSecondLineAndGetNoticed() {
        Order order = new Order(1);
        order.addListener(new Order.LinesListener() {
            @Override
            public void lineAdded(int line) {

            }

            public void lineRemoved(int line) {
                assertEquals("The lined removed must be 1", 1, line);
            }
        });

        Plate plate = new Plate(3, "Plate 3", "Description of plate 3", 5, null);
        order.addLine(plate, 1, null);
        plate = new Plate(5, "Plate 5", "Description of plate 5", 10.5f, null);
        order.addLine(plate, 1, null);
        plate = new Plate(10, "Plate 10", "Description of plate 10", 7.75f, null);
        order.addLine(plate, 1, null);
        order.removeLine(1);
    }

    @Test
    public void calculateTotal() {
        Order order = new Order(1);
        Plate plate = new Plate(1, "Plate 1", "Description of plate 1", 3.5f, null);
        order.addLine(plate, 1, null);
        plate = new Plate(2, "Plate 2", "Description of plate  2", 2, null);
        order.addLine(plate, 2, null);
        plate = new Plate(3, "Plate 3", "Description of plate 3", 4, null);
        order.addLine(plate, 1, null);
        plate = new Plate(8, "Plate 8", "Description of plate 8", 2, null);
        order.addLine(plate, 1, "Sin huevo");
        float total = order.calculateTotal();

        assertEquals(13.5f, total, 0.1f);
    }
}
