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
        if (activity==null){
            return;
        }
        if (activity.isFinishing()){
            return;
        }
        if(this.isAdded()) {
            return;
        }
        if (this==null){
            return;
        }
        if (this.isVisible()){
            return;
        }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(this, redDialog);
        transaction.commitAllowingStateLoss();
    }
    public void dismissDialog(Activity activity){
        if (activity==null){
            return;
        }
        if (activity.isFinishing()){
            return;
        }
        if (!this.isVisible()){
            super.dismissAllowingStateLoss();
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
