package com.example.a51425.mainuiframe.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Created by wr
 * 时间: 2018/1/10 15:00
 */
public class TestBean implements MultiItemEntity, Serializable{

    public static final int Title = 1;
    public static final int Content= 2;
    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
    public TestBean(int itemType) {
        this.itemType = itemType;
    }

    private boolean allowCheck = false;
    private String id;
    private String pid;
    private String showTitle;
    private int position;

    private List<String> child_children_list;
    private List<SpeciesBean.Children> childrenList;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isAllowCheck() {
        return allowCheck;
    }

    public void setAllowCheck(boolean allowCheck) {
        this.allowCheck = allowCheck;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<String> getChild_children_list() {
        return child_children_list;
    }

    public void setChild_children_list(List<String> child_children_list) {
        this.child_children_list = child_children_list;
    }

    public List<SpeciesBean.Children> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<SpeciesBean.Children> childrenList) {
        this.childrenList = childrenList;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "itemType=" + itemType +
                ", allowCheck=" + allowCheck +
                ", id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", showTitle='" + showTitle + '\'' +
                ", child_children_list=" + child_children_list +
                ", childrenList=" + childrenList +
                '}';
    }
}
