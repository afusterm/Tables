package io.keepcoding.tables.model;

import java.util.ArrayList;
import java.util.List;


public class Order {
    public interface OrderListener {
        void lineAdded(Line line);
        void lineRemoved(int line);
    }

    public class Line {
        private Course mCourse;
        private String mVariant;
        private int mUnits;

        private Line(Course course) {
            mCourse = course;
            mUnits = 1;
        }

        public Course getCourse() {
            return mCourse;
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
    private List<OrderListener> mListeners;
    private Table mTable;

    Order() {
        mLines = new ArrayList<>();
        mListeners = new ArrayList<>();
    }

    public float calculateTotal() {
        float total = 0;
        for (Line line: mLines) {
            Course course = line.getCourse();
            total += course.getPrice();
        }

        return total;
    }

    public void addListener(OrderListener listener) {
        mListeners.add(listener);
    }

    public Line getLine(int position) {
        return mLines.get(position);
    }

    public void addLine(Course course) {
        Line line = new Line(course);
        mLines.add(line);

        for (OrderListener listener: mListeners) {
            listener.lineAdded(line);
        }
    }

    public void removeLine(int line) {
        mLines.remove(line);

        for (OrderListener listener: mListeners) {
            listener.lineRemoved(line);
        }
    }

    public int size() {
        return mLines.size();
    }
}
