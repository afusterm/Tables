package io.keepcoding.tables.model;

import java.util.ArrayList;


public class Tables {
    private static ArrayList<Table> sTables;

    public static void createTables(int numberOfTables) {
        sTables = new ArrayList<>(numberOfTables);
        for (int i = 0; i < numberOfTables; i++) {
            sTables.add(new Table(i + 1));
        }
    }

    public static void createTables(final ArrayList<Table> tables) {
        sTables = tables;
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

    public static ArrayList<Table> allTables() {
        return sTables;
    }
}
