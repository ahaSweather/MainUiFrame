package com.example.a51425.mainuiframe.ui.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by 51425 on 2017/6/15.
 */

public class MyLinerlayout extends LinearLayout {
    public MyLinerlayout(Context context) {
        this(context,null);
    }

    public MyLinerlayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLinerlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int downX;

    private int downY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                // 父容器会把down事件传递下来，但是会拦截move事件，在down时请求父容器不拦截事件，是为了让父容器把move事件传递下来
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                // x方向移动的距离
                int diffX = moveX - downX;
                // y方向移动距离
                int diffY = moveY - downY;
                // 判断是上下还是左右
                if(Math.abs(diffX)>Math.abs(diffY)){// 2、左右滑动
                    // 当滑动的起始点大于100的时候自己处理
                    if ( diffX>0 && downX>100){
                        //横屏的话不处理
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            break;
                        }
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else{

                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                }else{// 1、上下滑动，不处理，让父容器拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
