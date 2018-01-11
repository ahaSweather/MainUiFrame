package com.example.a51425.mainuiframe.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.cyxk.wrframelibrary.base.APP;
import com.example.a51425.mainuiframe.R;


import java.util.ArrayList;

public class FlowLayout extends ViewGroup {
	
	/** 用于保存多行View的集合 */
	private ArrayList<ArrayList<View>> allLines = new ArrayList<ArrayList<View>>();
	private int horizotalSpacing = (int) APP.getContext().getResources().getDimension(R.dimen.dp_10);
	private int verticalSpacing = (int) APP.getContext().getResources().getDimension(R.dimen.dp_5);

	/** 因为只在代码中直接new，所以创建这个构造方法就可以了 */
	public FlowLayout(Context context) {
		super(context);
	}
	
	/**
	 * 测量FlowLayout及它的子View的宽高
	 * @param widthMeasureSpec 父容器希望FlowLayout的宽
	 * @param heightMeasureSpec 父容器希望FlowLayout的高
	 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 因为onMeasure会执行多次，所以每次测量的时候清空一下集合
		allLines.clear();
		// nohttp
		// measureChildren(widthMeasureSpec, heightMeasureSpec);
		// 获取父容器期望的宽
		int containerMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
		
		// 装一行View的集合
		ArrayList<View> oneLine = null;
		
		// 遍历所有子View并进行测量
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			
			// 0 = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
			child.measure(0, 0);	// 把测量规格传给子View，让子View完成测量
			
			// 如果是第0个需要创建新行，或者当前View的宽度大于一行中剩余的可用宽度也需要创建新行
			if (i == 0 || child.getMeasuredWidth() > getUsableWidth(containerMeasureWidth, oneLine)) {
				oneLine = new ArrayList<View>();
				allLines.add(oneLine);
			}
			
			oneLine.add(child);
		}
		
		// 设置FlowLayout的宽和高，宽就用父容器期望的宽，高为所有行的高 + 总的垂直padding + 总的垂直行间距
		int totalVerticalPadding = getPaddingTop() + getPaddingBottom();		// 总的垂直padding
		int totalVerticalSpacing = verticalSpacing * (allLines.size() - 1);		// 总的垂直行间距
		int containerMeasuredHeight = getAllLinesHeight() + totalVerticalPadding + totalVerticalSpacing;
		setMeasuredDimension(containerMeasureWidth, containerMeasuredHeight);
	}

	/** 获取所有行的高 */
	private int getAllLinesHeight() {
		return allLines.isEmpty() ? 0 : getChildAt(0).getMeasuredHeight() * allLines.size();
	}

	/**
	 * 获取一行当中可用的宽
	 * @param containerMeasuredWidth
	 * @param oneLine
	 * @return
	 */
	private int getUsableWidth(int containerMeasuredWidth, ArrayList<View> oneLine) {
		int oneLineWidth = 0;
		for (View view : oneLine) {
			oneLineWidth = oneLineWidth + view.getMeasuredWidth();
		}
		
		int totalHorizotalPadding = getPaddingLeft() + getPaddingRight();		// 总的水平padding
		int totalHorizotalSpacing = horizotalSpacing * (oneLine.size() - 1);	// 一行中所有的水平间距
		int usableWidth = containerMeasuredWidth - oneLineWidth - totalHorizotalPadding - totalHorizotalSpacing;
		return usableWidth;
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		int tempBottom = 0;	// 用于保存上一行View的Bottom位置
		
		// 遍历所有的行
		for (int rowIndex = 0; rowIndex < allLines.size(); rowIndex++) {
			ArrayList<View> oneLine = allLines.get(rowIndex);	// 取出一行View
			
			int tempRight = 0;	// 用于保存前一个View的Right位置
			
			// 每个View平均可以多获得的宽度
//			int averageUsableWidth = getUsableWidth(getMeasuredWidth(), oneLine) / oneLine.size();
			
			// 遍历一行View
			for (int columnIndex = 0; columnIndex < oneLine.size(); columnIndex++) {
				View child = oneLine.get(columnIndex);	// 取出一行中的一个View
				
				int childMeasuredWidth = child.getMeasuredWidth();
				int childMeasuredHeight = child.getMeasuredHeight();
				
				int childLeft = (columnIndex == 0) ? getPaddingLeft() : tempRight + horizotalSpacing;
				int childTop = (rowIndex == 0) ? getPaddingTop() : tempBottom + verticalSpacing;
				
				// 如果是最后一列，则把View的right安排在容器的最右边
//				int childRight = (columnIndex == oneLine.size() - 1)
//						         ? getMeasuredWidth() - getPaddingRight()
//						         : childLeft + childMeasuredWidth + averageUsableWidth;
				//不需要平分剩余空间
				int childRight = childLeft + childMeasuredWidth;

				int childBottom = childTop + childMeasuredHeight;
				child.layout(childLeft, childTop, childRight, childBottom);
				
				// 这个时候给子View的宽设置的跟以前测量的宽不一样，所以重新再测量一下，让居中属性生效
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childRight - childLeft, MeasureSpec.EXACTLY);
				int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childBottom - childTop, MeasureSpec.EXACTLY);
				child.measure(widthMeasureSpec , heightMeasureSpec);
				
				// 保存当前列的Right位置，下一个子View的left位置就是这个right位置
				tempRight = childRight;
			}
			
			// 保存当前行的Bottom位置，方便下一行使用，下一行的View的top位置就是这个bottom位置
			tempBottom = oneLine.get(0).getBottom();
		}
		
	}
	
}
