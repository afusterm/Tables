package io.keepcoding.tables.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Course;
import io.keepcoding.tables.model.Courses;
import io.keepcoding.tables.view.CourseRowViewHolder;
import io.keepcoding.tables.view.OnElementClick;

public class CoursesAdapter extends RecyclerView.Adapter<CourseRowViewHolder> {
    private OnElementClick mListener;

    public CoursesAdapter() {
    }

    @Override
    public CourseRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_course, parent, false);

        return new CourseRowViewHolder(root);
    }

    @Override
    public void onBindViewHolder(CourseRowViewHolder holder, final int position) {
        final Course course = Courses.get(position);
        holder.setCourse(course);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.clickedOn(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Courses.size();
    }

    public void setOnElementClickListener(@NonNull final OnElementClick listener) {
        mListener = listener;
    }
}
