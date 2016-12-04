package io.keepcoding.tables.model;

/**
 * Created by alejandro on 03/12/2016.
 */

public interface RESTClient {
    void get();
    void addGetListener(GetListener getListener);

    interface GetListener {
        void getReceived(String json);
    }
}
