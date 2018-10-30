package com.example.a51425.mainuiframe.data;

/**
 * 作者：Created by wr
 * 时间: 2018/1/9 18:16
 */
public class DataSource {
    private DataSource() {
    }

    private static DataSource instance;

    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null)
                    instance = new DataSource();
            }
        }
        return instance;
    }
}
