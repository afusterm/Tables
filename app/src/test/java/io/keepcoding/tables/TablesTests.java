package io.keepcoding.tables;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.tables.model.Table;
import io.keepcoding.tables.model.Tables;

public class TablesTests {
    @Test
    public void createTables() {
        List<Table> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Table(i));
        }

        Tables tables = new Tables(list);
        Assert.assertEquals("", 10, tables.size());
    }
}
