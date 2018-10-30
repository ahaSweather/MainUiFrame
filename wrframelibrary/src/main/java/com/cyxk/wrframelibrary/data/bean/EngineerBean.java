package com.cyxk.wrframelibrary.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 51425 on 2018/6/26.
 */

public class EngineerBean implements Parcelable{
    private String name;
    private String url;
    private Long startTime;
    private Long endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeValue(this.startTime);
        dest.writeValue(this.endTime);
    }

    public EngineerBean() {
    }

    protected EngineerBean(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.startTime = (Long) in.readValue(Long.class.getClassLoader());
        this.endTime = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<EngineerBean> CREATOR = new Creator<EngineerBean>() {
        @Override
        public EngineerBean createFromParcel(Parcel source) {
            return new EngineerBean(source);
        }

        @Override
        public EngineerBean[] newArray(int size) {
            return new EngineerBean[size];
        }
    };
}
