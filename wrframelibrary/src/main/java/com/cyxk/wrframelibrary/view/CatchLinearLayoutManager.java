package com.cyxk.wrframelibrary.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.cyxk.wrframelibrary.utils.LogUtils;


/**
 * Created by whg on 2018/3/27.
 */

public class CatchLinearLayoutManager extends LinearLayoutManager {

    public CatchLinearLayoutManager(Context context) {
        super(context);
    }

    public CatchLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CatchLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        try {
            super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
        } catch (IllegalArgumentException e) {
            LogUtils.logd("catch exception");
        }
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            LogUtils.logd("probe", "meet a IOOBE in RecyclerView");
        }
    }
}
