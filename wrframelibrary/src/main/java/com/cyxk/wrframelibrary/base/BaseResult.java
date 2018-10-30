package com.cyxk.wrframelibrary.base;

/**
 * Created by wonder on 2018/1/30.
 */

public class BaseResult<T> {
    public int code;
    public String msg;
    public T obj;
    public T data;
}
