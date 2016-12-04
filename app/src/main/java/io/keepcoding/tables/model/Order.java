package io.keepcoding.tables.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alejandro on 04/12/2016.
 */

public class Order {
    public interface LinesListener {
        void lineAdded(int line);
        void lineRemoved(int line);
    }

    public class Line {
        private Plate mPlate;
        private float mUnits;
        private String mVariant;

        private Line(Plate plate, float units, String variant) {
            mPlate = plate;
            mUnits = units;
            mVariant = variant;
        }

        public Plate getPlate() {
            return mPlate;
        }

        public float getUnits() {
            return mUnits;
        }

        public String getVariant() {
            return mVariant;
        }
    }

    private int mTable;
    private List<Line> mLines;
    private List<LinesListener> mListeners;

    public Order(int table) {
        mTable = table;
        mLines = new ArrayList<Line>();
        mListeners = new ArrayList<LinesListener>();
    }

    public int getTable() {
        return mTable;
    }

    public float calculateTotal() {
        float total = 0;
        for (Line line: mLines) {
            Plate plate = line.getPlate();
            total += plate.getPrice() * line.mUnits;
        }

        return total;
    }

    public void addListener(LinesListener listener) {
        mListeners.add(listener);
    }

    public void addLine(Plate plate, float units, String variant) {
        Line line = new Line(plate, units, variant);
        mLines.add(line);

        for (LinesListener listener: mListeners) {
            listener.lineAdded(mLines.size() - 1);
        }
    }

    public void removeLine(int position) {
        mLines.remove(position);

        for (LinesListener listener: mListeners) {
            listener.lineRemoved(position);
        }
    }
}
