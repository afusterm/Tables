package io.keepcoding.tables.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Course;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.view.CourseRowViewHolder;

public class CoursesAdapter extends RecyclerView.Adapter<CourseRowViewHolder> {
    private final LayoutInflater mLayoutInflater;

    public CoursesAdapter(@NonNull Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CourseRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = mLayoutInflater.inflate(R.layout.row_course, parent, false);

        return new CourseRowViewHolder(root);
    }

    @Override
    public void onBindViewHolder(CourseRowViewHolder holder, int position) {
        final Course course = Courses.get(position);
        holder.setCourse(course);
    }

    @Override
    public int getItemCount() {
        return Courses.size();
    }
}
