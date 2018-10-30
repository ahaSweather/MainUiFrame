package com.cyxk.wrframelibrary.base;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingbin on 2016/11/25
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> data = new ArrayList<>();
    public OnItemClickListener<T> listener;
    protected OnItemLongClickListener<T> onItemLongClickListener;

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.onBaseBindViewHolder(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<T> data) {
        this.data.addAll(data);
    }

    public void add(T object) {
        data.add(object);
    }

    public void clear() {
        data.clear();
    }

    public void remove(T object) {
        data.remove(object);
    }
    public void remove(int position) {
        data.remove(position);
    }
    public void removeAll(List<T> data) {
        this.data.retainAll(data);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }


    public List<T> getData() {
        return data;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setData(List<T> mDatas) {
        this.data= mDatas;
    }
    public void setVisibility1(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setVisibility2(View view) {
        if (view.getVisibility() == View.INVISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setInvisible(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void setGone(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }
    public void setText(TextView textView, String text){
        if (textView!=null && !TextUtils.isEmpty(text)){
            textView.setText(text);
        }
    }
    public void setText(TextView textView, String text,int color){
        if (textView!=null && !TextUtils.isEmpty(text)){
            textView.setText(text);
            if (color!=0) textView.setTextColor(color);
        }
    }

    public void setText(EditText editText, String text){
        if (editText!=null && !TextUtils.isEmpty(text)){
            editText.setText(text);
        }
    }
    public void setText(EditText editText, String text,int color){
        if (editText!=null && !TextUtils.isEmpty(text)){
            editText.setText(text);
            if (color!=0) editText.setTextColor(color);
        }
    }
}
