package com.example.a51425.mainuiframe.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.utils.LogUtil;


/**
 * 好用的下拉刷新、上拉加载
 *
 * @author chenyining
 * @version 4.4
 * @created 2016-10-28
 */
public class PullToRefreshView extends LinearLayout {
	private LayoutInflater mInflater;
	/** 头部View */
	private View mHeaderView;
	/** 脚部View */
	private View mFooterView;
	/** 头部View高度 */
	private int mHeaderViewHeight;
	private int mFooterViewHeight;
	private int mLastMotionY;
	/** 下拉刷新 */
	private static final int PULL_TO_REFRESH = 2;
	/** 手松刷新 */
	private static final int RELEASE_TO_REFRESH = 3;
	/** 正在刷新 */
	private static final int REFRESHING = 4;
	/** 已经加载到最后一条 */
	private static final int LOAD_MAXITEM = 5;

	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;

	private boolean enablePullTorefresh = true;
	private boolean enablePullLoadMoreDataStatus = true;
	private int mHeaderState;
	private int mFooterState;
	private AdapterView<?> mAdapterView;
	private int mPullState;
	private ScrollView mScrollView;
	private ImageView mHeaderTextView;
	private TextView mHeaderLoadingTextView;
	private TextView mFooterTextView;
	private ProgressBar mFooterProgressBar;
	private AnimationDrawable mAnimation;
	private ImageView mHeadImageView;

	private boolean isComment = false;//是否是评论列表

	public void setIsComment(boolean isComment){
		this.isComment = isComment;
	}

	public PullToRefreshView(Context context) {
		super(context);
		init();

	}

	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/** 加载头部View */
	private void init() {

		mInflater = LayoutInflater.from(getContext());
		addHeadView();

	}

	private void addHeadView() {
		mHeaderView = mInflater.inflate(R.layout.circle_refresh_head, this,
				false);
		mHeadImageView = (ImageView) mHeaderView.findViewById(R.id.loadingIv);
		mHeaderTextView = (ImageView) mHeaderView
				.findViewById(R.id.iv_circle_head);
		mHeaderLoadingTextView = (TextView) mHeaderView
				.findViewById(R.id.tv_circle_head);
		mAnimation = (AnimationDrawable) mHeadImageView.getBackground();
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mHeaderViewHeight);
		// 设置topMargin的值为负的header View高度,即将其隐藏在最上方
		params.topMargin = -(mHeaderViewHeight);
		// mHeaderView.setLayoutParams(params1);
		addView(mHeaderView, params);
	}

	private void addFooterView() {
		// footer view
		mFooterView = mInflater.inflate(R.layout.circle_refresh_foot, this,
				false);
		mFooterTextView = (TextView) mFooterView
				.findViewById(R.id.tv_circle_foot);
		mFooterProgressBar = (ProgressBar)mFooterView.findViewById(R.id.pb_circle_foot);
		// footer layout
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mFooterViewHeight);
		// 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
		addView(mFooterView, params);
	}

	/** 加载脚部View */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// footer view 在此添加保证添加到linearlayout中的最后
		addFooterView();
		initContentAdapterView();
	}

	/** 测量高度 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	private int changingHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		// 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,感谢网友yufengzungzhe的指出
		// 表示如果是在上拉后一段距离,然后直接下拉
		if (deltaY > 0 && mPullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= mHeaderViewHeight) {
			return params.topMargin;
		}
		// 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
		if (deltaY < 0 && mPullState == PULL_DOWN_STATE
				&& Math.abs(params.topMargin) >= mHeaderViewHeight) {
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}

	private void headerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		mHeaderTextView.setVisibility(View.VISIBLE);
		mHeaderLoadingTextView.setVisibility(View.VISIBLE);
		// 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
			mHeaderState = RELEASE_TO_REFRESH;
		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
			mHeaderState = PULL_TO_REFRESH;
		}
	}

	private void footerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		String aa;
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterState != RELEASE_TO_REFRESH) {
			if (mFooterState == LOAD_MAXITEM) {
				if(isComment){
					aa = "评论加载完成";
				}else{
					aa = "已经加载到最后一条";
				}
				mFooterTextView.setText(aa);
			} else {
				aa = "松开加载更多";
				mFooterTextView.setText(aa);
				mFooterState = RELEASE_TO_REFRESH;
			}
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			if (mFooterState == LOAD_MAXITEM) {
				if(isComment){
					aa = "评论加载完成";
				}else{
					aa = "已经加载到最后一条";
				}
				mFooterTextView.setText(aa);
			} else {
				aa = "上拉加载更多";
				mFooterTextView.setText(aa);
				mFooterState = PULL_TO_REFRESH;
			}
		}
	}

	private boolean mLock;

	public boolean onTouchEvent(MotionEvent event) {
		if (mLock) {
			return true;
		}
		int y = (int) event.getRawY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// onInterceptTouchEvent已经记录
				// mLastMotionY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaY = y - mLastMotionY;
				if (mPullState == PULL_DOWN_STATE) {
					// PullToRefreshView执行下拉
					headerPrepareToRefresh(deltaY);
				} else if (mPullState == PULL_UP_STATE) {
					// PullToRefreshView执行上拉
					footerPrepareToRefresh(deltaY);
				}
				mLastMotionY = y;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				int topMargin = getHeaderTopMargin();
				if (mPullState == PULL_DOWN_STATE) {
					// 开始刷新
//					headerRefreshing();
					LogUtil.e("topMargin___"+topMargin);
					LogUtil.e("mHeaderViewHeight——————"+mHeaderViewHeight);
					LogUtil.e("mHeaderViewHeight/2——————"+mHeaderViewHeight/2);
					if (topMargin<0) {
						if (Math.abs(topMargin)<Math.abs(mHeaderViewHeight)/2){
							// 开始刷新
							headerRefreshing();
						}else{
							// 还没有执行刷新，重新隐藏
							setHeaderTopMargin(-mHeaderViewHeight);
						}

					} else {
//						setHeaderTopMargin(-mHeaderViewHeight);
						headerRefreshing();
					}
				} else if (mPullState == PULL_UP_STATE) {
					// 开始执行footer 刷新
					footerRefreshing();
					/*if (Math.abs(topMargin) >= mHeaderViewHeight
							+ mFooterViewHeight) {
						// 开始执行footer 刷新
						footerRefreshing();
					} else {
						// 还没有执行刷新，重新隐藏
						setHeaderTopMargin(-mHeaderViewHeight);
					}*/
				}
				break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int y = (int) e.getRawY();
		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 首先拦截down事件,记录y坐标
				mLastMotionY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				// deltaY > 0 是向下运动,< 0是向上运动
				int deltaY = y - mLastMotionY;
//				LogUtil.e("deltaY____"+deltaY);
				if (isRefreshViewScroll(deltaY)) {
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				break;
		}

		return false;
	}

	/**
	 * 是否应该到了父View,即PullToRefreshView滑动
	 *
	 * @param deltaY
	 *            , deltaY > 0 是向下运动,< 0是向上运动
	 * @return
	 */
	private boolean isRefreshViewScroll(int deltaY) {
		if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
			return false;
		}
		// 对于ListView和GridView
		if (mAdapterView != null) {
			// 子view(ListView or GridView)滑动到最顶端
			if (deltaY > 0) {
				// 判断是否禁用下拉刷新操作
				if (!enablePullTorefresh) {
					return false;
				}
				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// 如果mAdapterView中没有数据,不拦截
					return false;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 11) {// 这里之前用3可以判断,但现在不行,还没找到原因
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				// 判断是否禁用上拉加载更多操作
				if (!enablePullLoadMoreDataStatus) {
					return false;
				}
				View lastChild = mAdapterView.getChildAt(mAdapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					// 如果mAdapterView中没有数据,不拦截
					return false;
				}
				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
				// 等于父View的高度说明mAdapterView已经滑动到最后
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
						.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		// 对于ScrollView
		if (mScrollView != null) {
			// 子scroll view滑动到最顶端
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
					+ mScrollView.getScrollY()) {
				if (!enablePullLoadMoreDataStatus) {
					return false;
				}
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * 正在刷新
	 */
	public void headerRefreshing() {
		mHeaderState = REFRESHING;
		setHeaderTopMargin(0);
		mHeaderTextView.setVisibility(View.GONE);
		mHeadImageView.setVisibility(View.VISIBLE);
		mHeaderLoadingTextView.setVisibility(View.VISIBLE);
		mHeadImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	/**
	 * 正在加载
	 */
	private void footerRefreshing() {
		int top = mHeaderViewHeight + mFooterViewHeight;
		if (!(mFooterState == LOAD_MAXITEM)) {
			mFooterState = REFRESHING;
			String aa = "正在加载";
			mFooterTextView.setText(aa);
			mFooterProgressBar.setVisibility(View.VISIBLE);
			setHeaderTopMargin(-top);
			if (mOnFooterRefreshListener != null) {
				mOnFooterRefreshListener.onFooterRefresh(this);
			}
		} else {
			setHeaderTopMargin(-mHeaderViewHeight);
		}
	}

	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
	}

	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	public void onFooterRefreshComplete(boolean isLoadMaxItem) {
		mFooterProgressBar.setVisibility(View.GONE);
		if (isLoadMaxItem) {
			setHeaderTopMargin(-mHeaderViewHeight);
			if(isComment){
				mFooterTextView.setText("评论加载完成");
			}else{
				mFooterTextView.setText("已经加载到最后一条");
			}

			mFooterState = LOAD_MAXITEM;
		} else {

			setHeaderTopMargin(-mHeaderViewHeight);
			mFooterTextView.setText("上拉加载更多");
			mFooterState = PULL_TO_REFRESH;
		}
	}

	public void onHeaderRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeadImageView.setVisibility(View.GONE);
		mHeaderState = PULL_TO_REFRESH;
	}

	/** 脚部监听 */
	private OnFooterRefreshListener mOnFooterRefreshListener;

	/** 脚部监听 */
	public interface OnFooterRefreshListener {
		public void onFooterRefresh(PullToRefreshView view);
	}

	/** 头部监听 */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;

	/** 头部监听 */
	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh(PullToRefreshView view);
	}

	public void setmOnFooterRefreshListener(
			OnFooterRefreshListener mOnFooterRefreshListener) {
		this.mOnFooterRefreshListener = mOnFooterRefreshListener;
	}

	public void setmOnHeaderRefreshListener(
			OnHeaderRefreshListener mOnHeaderRefreshListener) {
		this.mOnHeaderRefreshListener = mOnHeaderRefreshListener;
	}

	public boolean isEnablePullTorefresh() {
		return enablePullTorefresh;
	}

	public void setEnablePullTorefresh(boolean enablePullTorefresh) {
		this.enablePullTorefresh = enablePullTorefresh;
	}

	public boolean isEnablePullLoadMoreDataStatus() {
		return enablePullLoadMoreDataStatus;
	}

	public void setEnablePullLoadMoreDataStatus(
			boolean enablePullLoadMoreDataStatus) {
		this.enablePullLoadMoreDataStatus = enablePullLoadMoreDataStatus;
	}
}
