package com.example.a51425.mainuiframe.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 51425 on 2017/3/26.
 */
public class ViewPagerMain  extends ViewPager{
    private boolean mIsScroll=true;

    public ViewPagerMain(Context context) {
        super(context);
    }

    public ViewPagerMain(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScroll==true){
            return super.onInterceptTouchEvent(ev);
        }else{
            return mIsScroll;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsScroll==true){
            return super.onTouchEvent(ev);
        }else{
            return mIsScroll;
        }

    }

    public void setIsScroll(boolean isScroll){
        mIsScroll = isScroll;
    }
}
