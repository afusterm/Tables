package io.keepcoding.tables.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Order implements Serializable {
    public interface LinesListener {
        void lineAdded(Line line);
        void lineRemoved(int line);
    }

    public class Line implements Serializable {
        private Plate mPlate;
        private String mVariant;
        private int mUnits;

        private Line(Plate plate) {
            mPlate = plate;
        }

        public Plate getPlate() {
            return mPlate;
        }

        public String getVariant() {
            return mVariant;
        }

        public void setVariant(String variant) {
            mVariant = variant;
        }

        public int getUnits() {
            return mUnits;
        }

        public void setUnits(int units) {
            mUnits = units;
        }
    }

    private List<Line> mLines;
    private List<LinesListener> mListeners;
    private Table mTable;

    Order() {
        mLines = new ArrayList<Line>();
        mListeners = new ArrayList<LinesListener>();
    }

    public float calculateTotal() {
        float total = 0;
        for (Line line: mLines) {
            Plate plate = line.getPlate();
            total += plate.getPrice();
        }

        return total;
    }

    public void addListener(LinesListener listener) {
        mListeners.add(listener);
    }

    public Line getLine(int position) {
        return mLines.get(position);
    }

    public void addLine(Plate plate) {
        Line line = new Line(plate);
        mLines.add(line);

        for (LinesListener listener: mListeners) {
            listener.lineAdded(line);
        }
    }

    public void removeLine(int line) {
        mLines.remove(line);

        for (LinesListener listener: mListeners) {
            listener.lineRemoved(line);
        }
    }

    public int size() {
        return mLines.size();
    }
}
