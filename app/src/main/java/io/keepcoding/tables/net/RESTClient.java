package io.keepcoding.tables.net;

public interface RESTClient {
    void get();
    void setGetListener(GetListener getListener);

    interface GetListener {
        void getReceived(String json);
        void errorOnDownload();
    }
}
