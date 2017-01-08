package io.keepcoding.tables.net;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CoursesRESTClient implements RESTClient {

    private String mUrl;
    private GetListener mListener;

    public CoursesRESTClient(final @NonNull String url) {
        mUrl = url;
    }

    public void setGetListener(final GetListener listener) {
        mListener = listener;
    }

    public void get() {
        try {
            URLConnection connection = new URL(mUrl).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();

            if (mListener != null) {
                mListener.getReceived(sb.toString());
            }
        } catch (IOException e) {
            if (mListener != null) {
                mListener.errorOnDownload();
            }
        }
    }
}
