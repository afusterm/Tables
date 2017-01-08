package io.keepcoding.tables;

import org.junit.Test;

import io.keepcoding.tables.model.Course;
import io.keepcoding.tables.model.Order;
import io.keepcoding.tables.model.Table;

import static org.junit.Assert.assertEquals;


public class OrderUnitTest {

    @Test
    public void addOneLine() {
        Table table = new Table(0);
        Order order = table.getOrder();

        Course course = new Course("Course 1", 5f, "picture url", null);
        order.addLine(course);
        Order.Line line = order.getLine(0);
        line.setVariant("Variant of course 1");
        line.setUnits(2);

        assertEquals("The number of lines must be 1", 1, order.size());
        assertEquals("Variant is not correct", "Variant of course 1", line.getVariant());
        assertEquals("The number of units must be 2", 2, line.getUnits());
    }

    @Test
    public void removeSecondLine() {
        Table table = new Table(0);
        Order order = table.getOrder();

        Course course = new Course("Course 3", 5, "picture url", null);
        order.addLine(course);
        course = new Course("Course 5", 10.5f, "picture url", null);
        order.addLine(course);
        course = new Course("Course 10", 7.75f, "picture url", null);
        order.addLine(course);

        assertEquals("The number of lines must be 3", 3, order.size());

        order.removeLine(1);

        assertEquals("The number of lines must be 2", 2, order.size());
    }

    @Test
    public void calculateTotal() {
        Table table = new Table(0);
        Order order = table.getOrder();
        Course course = new Course("Course 1", 3.5f, "picture url", null);
        order.addLine(course);
        course = new Course("Course 2", 2, "picture url", null);
        order.addLine(course);
        course = new Course("Course 3", 4, "picture url", null);
        order.addLine(course);
        course = new Course("Course 8", 2, "picture url", null);
        order.addLine(course);
        float total = order.calculateTotal();

        assertEquals(11.5f, total, 0.1f);
    }
}
