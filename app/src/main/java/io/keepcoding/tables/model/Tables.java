package io.keepcoding.tables.model;

import java.util.ArrayList;
import java.util.List;


public class Tables {
    private static List<Table> sTables;

    public static void createTables(int numberOfTables) {
        sTables = new ArrayList<>(numberOfTables);
        for (int i = 0; i < numberOfTables; i++) {
            sTables.add(new Table(i + 1));
        }
    }

    public static Table get(int number) {
        return sTables.get(number);
    }

    public static int size() {
        if (sTables == null) {
            return 0;
        }

        return sTables.size();
    }
}
