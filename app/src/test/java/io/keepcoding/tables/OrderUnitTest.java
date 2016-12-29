package io.keepcoding.tables;

import org.junit.Test;

import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Plate;
import io.keepcoding.tables.model.Table;

import static org.junit.Assert.assertEquals;


public class OrderUnitTest {

    @Test
    public void addOneLine() {
        Table table = new Table(0);
        Order order = table.getOrder();

        Plate plate = new Plate(1, "Plate 1", "Description of plate 1", 5f, null);
        order.addLine(plate);
        Order.Line line = order.getLine(0);
        line.setVariant("Variant of plate 1");
        line.setUnits(2);

        assertEquals("The number of lines must be 1", 1, order.size());
        assertEquals("Variant is not correct", "Variant of plate 1", line.getVariant());
        assertEquals("The number of units must be 2", 2, line.getUnits());
    }

    @Test
    public void removeSecondLine() {
        Table table = new Table(0);
        Order order = table.getOrder();

        Plate plate = new Plate(3, "Plate 3", "Description of plate 3", 5, null);
        order.addLine(plate);
        plate = new Plate(5, "Plate 5", "Description of plate 5", 10.5f, null);
        order.addLine(plate);
        plate = new Plate(10, "Plate 10", "Description of plate 10", 7.75f, null);
        order.addLine(plate);

        assertEquals("The number of lines must be 3", 3, order.size());

        order.removeLine(1);

        assertEquals("The number of lines must be 2", 2, order.size());
    }

    @Test
    public void calculateTotal() {
        Table table = new Table(0);
        Order order = table.getOrder();
        Plate plate = new Plate(1, "Plate 1", "Description of plate 1", 3.5f, null);
        order.addLine(plate);
        plate = new Plate(2, "Plate 2", "Description of plate  2", 2, null);
        order.addLine(plate);
        plate = new Plate(3, "Plate 3", "Description of plate 3", 4, null);
        order.addLine(plate);
        plate = new Plate(8, "Plate 8", "Description of plate 8", 2, null);
        order.addLine(plate);
        float total = order.calculateTotal();

        assertEquals(11.5f, total, 0.1f);
    }
}
