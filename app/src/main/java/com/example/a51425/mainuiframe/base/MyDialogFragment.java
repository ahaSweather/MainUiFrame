package com.example.a51425.mainuiframe.base;

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

import com.example.a51425.mainuiframe.utils.LogUtil;

import java.lang.ref.WeakReference;

public class MyDialogFragment extends DialogFragment {

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealWith(msg);
        }
    };
    private Activity mActivity;

    protected void dealWith(Message msg) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        this.mActivity = activity;
    }

    protected Activity getMyActivity() {
        return mActivity;
    }

    @Override
    public void onStart() {
        super.onStart();
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
     * 弹出Dialog
     * @param fragmentManager
     * @param redDialog
     */
    public void showDialog(FragmentManager fragmentManager, String redDialog) {
        try{
            if (!this.isAdded()

                    && !this.isVisible()

                    && !this.isRemoving()) {
                this.show(fragmentManager.beginTransaction(), redDialog);
//            this.showDialog(getFragmentManager(), "redDialog");
            }
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
            return;
        }

    }

    /**
     * 弹出Dialog
     * @param activity
     * @param redDialog
     */
    public void showDialog(FragmentActivity activity, String redDialog) {

        if(this.isAdded()) {
            LogUtil.e("之前被添加过");
//            (activity).getSupportFragmentManager().beginTransaction().remove(this).commit();
            return;
        }
        if (activity==null){
            LogUtil.e("依赖的activity为null");
            return;
        }
        if (activity.isFinishing()){
            LogUtil.e("依赖的activity为被干掉");
            return;
        }
        if (this==null){
            LogUtil.e("找不到dialog");
            return;
        }
        if (this.isVisible()){
            LogUtil.e("dialog 正在显示");
            return;
        }
        LogUtil.e("开始正式的提交");
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(this, redDialog);
        transaction.commitAllowingStateLoss();
//        transaction.show(this);

//        if(null != activity && !activity.isFinishing() && (null == getDialog() || !getDialog().isShowing())) {
//            FragmentManager fragmentManager = activity.getSupportFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.add(this, redDialog);
//            transaction.commitAllowingStateLoss();
//            transaction.show(this);
//        }

    }
    public void dismissDialog(Activity activity){
//        if (null != activity && !activity.isFinishing() && null != getDialog() && getDialog().isShowing()) {
//            super.dismissAllowingStateLoss();
//        }
        if (activity==null){
            return;
        }
        if (activity.isFinishing()){
            return;
        }
        if (!this.isVisible()){
            LogUtil.e("取消dialogFragment");
            super.dismissAllowingStateLoss();
        }else{
            LogUtil.e("dialogFragment没有显示");
        }
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
    protected Dialog MyCreateDialog(int style){
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
