package com.cyxk.wrframelibrary.data.source;

/**
 * Created by 51425 on 2018/6/26.
 */

public class NewsDataSource {
    private NewsDataSource() {
    }

    private static NewsDataSource instance;

    public static NewsDataSource getInstance() {
        if (instance == null) {
            synchronized (NewsDataSource.class) {
                if (instance == null)
                    instance = new NewsDataSource();
            }
        }
        return instance;
    }
}
