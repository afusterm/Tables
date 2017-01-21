package io.keepcoding.tables.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import io.keepcoding.tables.R;
import io.keepcoding.tables.model.Allergen;
import io.keepcoding.tables.model.Course;


public class CourseRowViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = CourseRowViewHolder.class.getCanonicalName();

    private ImageView mPicture;
    private TextView mName;
    private TextView mPrice;
    private LinearLayout mAllergensLayout;
    private WeakReference<Context> mContext;

    public CourseRowViewHolder(View rowCourse) {
        super(rowCourse);

        mContext = new WeakReference<>(rowCourse.getContext());
        mName = (TextView) rowCourse.findViewById(R.id.row_course_name);
        mPrice = (TextView) rowCourse.findViewById(R.id.row_course_price);
        mPicture = (ImageView) rowCourse.findViewById(R.id.row_course_image);
        mAllergensLayout = (LinearLayout) rowCourse.findViewById(R.id.row_course_allergens_layout);
    }

    public void setCourse(final @NonNull Course course) {
        mName.setText(course.getName());
        mPrice.setText(String.valueOf(course.getPrice()) + " " + mContext.get().getString(R.string.currency));
        setImageOnImageView(mPicture, course.getPictureURL());

        for (Allergen allergen: course.getAllergens()) {
            ImageView icon = new ImageView(mContext.get());
            setImageOnImageView(icon, allergen.getIconURL());
            mAllergensLayout.addView(icon);
        }
    }

    private void setImageOnImageView(final @NonNull ImageView imageView, final @NonNull String imageURL) {
        imageView.setImageResource(R.drawable.no_image);

        Thread downloader = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getPictureFromURL(imageURL, mContext.get());
                if (bitmap != null) {
                    setImageBitmapOnUIThread(imageView, bitmap);
                }
            }
        });

        downloader.start();
    }

    private void setImageBitmapOnUIThread(final ImageView imageView, final Bitmap bitmap) {
        Handler handler = new Handler(mContext.get().getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        };

        handler.post(runnable);
    }

    private Bitmap getPictureFromURL(final @NonNull String url, final @NonNull Context context) {
        int lastSlash = url.lastIndexOf('/');
        final String fileName = url.substring(lastSlash + 1);

        File imageFile = new File(context.getCacheDir(), fileName);

        if (imageFile.exists()) {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }

        Bitmap bmp = getBitmapFromURL(url);

        if (bmp != null) {
            saveBitmap(imageFile, bmp);
        }

        return bmp;
    }

    private Bitmap getBitmapFromURL(final @NonNull String url) {
        if (url == null) {
            return null;
        }

        Bitmap bmp = null;
        InputStream inputStream = null;

        try {
            inputStream = new java.net.URL(url).openStream();
            bmp = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return bmp;
    }

    @NonNull
    private void saveBitmap(final @NonNull File imageFile, final @NonNull Bitmap bmp) {
        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(imageFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        bmp.compress(Bitmap.CompressFormat.PNG, 90, outputStream);

        try {
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
