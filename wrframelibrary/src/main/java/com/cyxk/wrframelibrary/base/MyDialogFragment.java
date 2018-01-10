package com.cyxk.wrframelibrary.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.SharedPreferanceUtils;

import java.lang.ref.WeakReference;

public class MyDialogFragment extends DialogFragment {

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealWith(msg);
        }
    };
    protected SharedPreferanceUtils sharedPreferanceUtils = SharedPreferanceUtils.getSp();
    private BaseActivity mActivity;
    private boolean mShareClick = true;

    protected void dealWith(Message msg) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        this.mActivity = (BaseActivity) activity;
    }

    public BaseActivity getMyActivity() {
        return mActivity;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 让dialogFragment全屏显示
//        getDialog().getWindow().setBackgroundDrawable( new ColorDrawable(Color.WHITE) );
////        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);
//        }

    }

    @Override
    public void onDestroy() {
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
//        initListener();
//        initData();
    }

    protected void initData() {


    }


    protected void initListener() {

    }


    /**
     * 弹出Dialog dialogFragmnet 的show 和 hide 最好按照下面的来写，已经踩了不少坑了
     * @param activity
     * @param redDialog
     */
    public void showDialog(FragmentActivity activity, String redDialog) {
        try{
            LogUtil.e("111");
            if (activity==null){
                LogUtil.e("222");
                return;
            }
            if (activity.isFinishing()){
                LogUtil.e("333");
                return;
            }
            if(this.isAdded()) {
                LogUtil.e("444");
                return;
            }

            if (this==null){
                LogUtil.e("555");
                return;
            }
            if (this.isVisible() ){
                LogUtil.e("666");
                return;
            }
            LogUtil.e("777");
            WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(activity);
            FragmentActivity activityNew = (BaseActivity) activityWeakReference.get();
            FragmentManager fragmentManager = activityNew.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(this, redDialog);
            transaction.commitAllowingStateLoss();
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }

    }
    public void dismissDialog(Activity activity){
        try{
            WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(activity);
            FragmentActivity activityNew = (BaseActivity) activityWeakReference.get();
            if (activityNew==null){
                return;
            }
            if (activityNew.isFinishing()){
                return;
            }
            super.dismissAllowingStateLoss();
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }



//        activity = null;
    }

    /**
     * 创建Dialog
     * @return
     */
    protected Dialog MyCreateDialog(){
        Dialog dialog = null;
        WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(getMyActivity());
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            dialog = new Dialog(activity);
        } else {
            dialog = new Dialog(getMyActivity());
        }
        return dialog;
    }
    /**
     * 创建Dialog
     * @return
     */
    protected Dialog createDialog(int style){
        Dialog dialog = null;
        WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(getMyActivity());
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            dialog = new Dialog(activity,style);
        } else {
            dialog = new Dialog(getMyActivity(),style);

        }
        return dialog;
    }





}
