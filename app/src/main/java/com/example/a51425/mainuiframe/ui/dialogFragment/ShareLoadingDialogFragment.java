package com.example.a51425.mainuiframe.ui.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import com.cyxk.wrframelibrary.base.MyDialogFragment;
import com.example.a51425.mainuiframe.R;

/**
 * Created by 51425 on 2017/5/9.
 */

public class ShareLoadingDialogFragment extends MyDialogFragment {

    private TextView mTVShow;
    private Context context;

    public void ShareLoadingDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = MyCreateDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.share_loding);
        mTVShow = (TextView) dialog.findViewById(R.id.tv_loading);
        mTVShow.setText("准备分享中请稍后");

        return dialog;

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.share_loding, null);
//        context = inflater.getContext();
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null) {
//            parent.removeView(view);
//        }
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mTVShow = (TextView) view.findViewById(R.id.tv_loading);
//        mTVShow.setText("准备分享中请稍后");
//
//        return view;
//
//    }

    public void setShowText(String showText) {
        mTVShow.setText(showText);
    }




}
