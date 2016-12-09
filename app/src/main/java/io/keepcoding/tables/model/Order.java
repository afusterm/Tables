package io.keepcoding.tables.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alejandro on 04/12/2016.
 */

public class Order {
    public interface LinesListener {
        void lineAdded(Line line);
        void lineRemoved(Line line);
    }

    public class Line {
        private Plate mPlate;
        private String mVariant;

        private Line(Plate plate, String variant) {
            mPlate = plate;
            mVariant = variant;
        }

        public Plate getPlate() {
            return mPlate;
        }

        public String getVariant() {
            return mVariant;
        }
    }

    private List<Line> mLines;
    private List<LinesListener> mListeners;

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

    public void addLine(Plate plate, String variant) {
        Line line = new Line(plate, variant);
        mLines.add(line);

        for (LinesListener listener: mListeners) {
            listener.lineAdded(line);
        }
    }

    public void removeLine(Line line) {
        mLines.remove(line);

        for (LinesListener listener: mListeners) {
            listener.lineRemoved(line);
        }
    }

    public List<Line> getLines() {
        return mLines;
    }
}
