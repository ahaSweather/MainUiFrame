package com.cyxk.wrframelibrary.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class BasicAdapter<T> extends BaseAdapter {
	
	protected List<T> datas;

	public BasicAdapter(List<T> datas) {
		this.datas = datas;
	}
	
	/** 请相信我吧，这个方法的返回值绝对不会是null的，最多会是datas.isEmpty() */
	public List<T> getDatas() {
		if (datas == null) {
			datas = new ArrayList<T>();
		}
		return datas;
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	

	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {
		Object holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(position), null);
			holder = createViewHolder(position, convertView);
			convertView.setTag(holder);
		} else {
			holder = convertView.getTag();
		}

		
		// 获取position位置的数据
		T data = datas.get(position);
		
		// 显示数据
		showData(position, holder, data,datas);
		
		return convertView;
	}
	
	/** 
	 * 返回一个用于创建item条目的布局id 
	 * @param position 要生成item的位置
	 * */
	public abstract int getItemLayoutId(int position);
	
	/**
	 * 创建ViewHolder，并且把convertView中的控件查找到保存到ViewHolder
	 * @param position
	 * @param convertView
	 * @return
	 */
	public abstract Object createViewHolder(int position, View convertView);
	
	/**
	 * 显示数据到viewHolder中的控件中
	 * @param position
	 * @param viewHolder
	 * @param data
	 */
	public abstract void showData(int position, Object viewHolder, T data, List datas);
	public void setDatas(List<T> datas) {
		ArrayList<T> datas1 = (ArrayList<T>) datas;
		this.datas = datas1;
		notifyDataSetChanged();
	}

}
















