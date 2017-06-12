package com.example.a51425.mainuiframe.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.constant.Constant;
import com.example.a51425.mainuiframe.interfaces.GetResultListener;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * 作者：wr
 * 时间: 2017/4/19 18:27
 */
public class ShareUtils {


    /**
     * 通过intent方式分享内容到微信朋友圈（朋友圈可以是图片加文字一起分享）
     *
     * @param shareWord
     * @param fileUri
     */
    public static void throughIntentShareWXCircle(String shareWord, Uri fileUri) {
        if (!AppUtils.checkApkExist(APP.getContext(), "com.tencent.mm")) {
            ToastUtil.showToast(APP.getContext(), "亲，你还没安装微信");
            return;
        }
        try{
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("Kdescription", shareWord);
            intent.putExtra(Intent.EXTRA_STREAM,fileUri);
            APP.getContext().startActivity(intent);
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }


    }



    /**
     * 跳转官方安装网址
     */
    public void toInstallWebView(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setData(Uri.parse(url));
        APP.getContext().startActivity(intent);
    }

    /** （已过时）
     * @param appId        来源id
     * @param packageName  来源包名
     * @param shareTitle   分享标题
     * @param shareContent 分享内容
     * @param shareUrl     分享链接
     * @param type         分享方式 0 好友 1 朋友圈 2 收藏
     * @param shareBitmap  分享图片
     */
    public static void shareWX(WeakReference<Activity> weakReference, String appId, String packageName, String shareTitle, String shareContent, String shareUrl, int type, Bitmap shareBitmap,GetResultListener onShareLitener) {
//        Bitmap thumb = BitmapFactory.decodeResource(WebActivity.this.getResources(), R.drawable.open_money_icon);
        LogUtil.e("shareWX_______");
        Bitmap localBitmap2 = Bitmap.createScaledBitmap(shareBitmap, 150, 150, true);
//        Bitmap localBitmap2 = shareBitmap;
        if (shareBitmap!=null){
            shareBitmap.recycle();
            shareBitmap=null;
        }
        //通过原始的微信sdk来组装参数
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = shareUrl;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
        localWXMediaMessage.title = shareTitle;
        localWXMediaMessage.description = shareContent;
        localWXMediaMessage.thumbData = (bmpToByteArray(localBitmap2, true));
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene = type;
        //在分享的时候不调用sdk中原有的分享代码，改调用自己的，这里需要注意不要使用新的jar包，里面有的方法已经取消了，就用项目里的
        WxShare.sendReq(weakReference, onShareLitener, localReq, appId, packageName);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        LogUtil.e("result___长度" + result.length);
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void getHttpBitmap(final String shareImage, final GetResultListener shareImageListener) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        URL pictureUrl = null;
                        InputStream in = null;
                        Bitmap bitmap = null;
                        try {
                            pictureUrl = new URL(shareImage);
                            in = pictureUrl.openStream();
                            SoftReference<Bitmap> bitmapSoftReference = new SoftReference<>(BitmapFactory.decodeStream(in));
                            bitmap = bitmapSoftReference.get();
                            shareImageListener.onSuccess(bitmap);
                        } catch (Exception e) {
                            LogUtil.e(Log.getStackTraceString(e));
//                            ResultException.resultFailureHandle(0);
                            shareImageListener.onError();
                        } finally {
                            try {
                                if (in!=null){
                                    in.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                shareImageListener.onError();
                            }
                        }
                    }
                }

        ).start();


    }

    /**
     *  做分享前的准备，判断当前有哪个应用能进行分享 （使用回调方式，已过时）
     * @param weakReference
     * @param shareTitle
     * @param share_word
     * @param shareUrl
     * @param type
     * @param bitmap
     */
    public static void shareWXReady(WeakReference<Activity> weakReference, String shareTitle, String share_word, String shareUrl, int type, Bitmap bitmap, GetResultListener onShareLitener) {
        LogUtil.e("shareWXReady——————");

        try{
            if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQ)){
                LogUtil.e("安装了QQ");
                ShareUtils.shareWX(weakReference,Constant.WEIXINAPPKEYQQ,Constant.WEIXINAPPPACKAGEQQ,shareTitle
                        ,share_word,shareUrl,type,bitmap,onShareLitener
                );
            }else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEUC)){
                LogUtil.e("安装了uc");
                ShareUtils.shareWX(weakReference,Constant.WEIXINAPPKEYUC,Constant.WEIXINAPPPACKAGEUC,shareTitle
                        ,share_word,shareUrl,type,bitmap,onShareLitener);
            }else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQBROWSER)){
                LogUtil.e("安装了qqBrowser");
                ShareUtils.shareWX(weakReference,Constant.WEIXINAPPKEYQQBROWSER,Constant.WEIXINAPPPACKAGEQQBROWSER,shareTitle
                        ,share_word,shareUrl,type,bitmap,onShareLitener);
            }else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGENEWSTODAY)){
                LogUtil.e("安装了今日头条");
                ShareUtils.shareWX(weakReference,Constant.WEIXINAPPKEYNEWSTODAY,Constant.WEIXINAPPPACKAGENEWSTODAY,shareTitle
                        ,share_word,shareUrl,type,bitmap,onShareLitener);

            }else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEBAIDU)){
                LogUtil.e("安装了百度");
                ShareUtils.shareWX(weakReference,Constant.WEIXINAPPKEYBAIDU,Constant.WEIXINAPPPACKAGEBAIDU,shareTitle
                        ,share_word,shareUrl,type,bitmap,onShareLitener);
            }else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGESINA)){
                LogUtil.e("安装了sina");
                ShareUtils.shareWX(weakReference,Constant.WEIXINAPPKEYSINA,Constant.WEIXINAPPPACKAGESINA,shareTitle
                        ,share_word,shareUrl,type,bitmap,onShareLitener);
            }else{
                LogUtil.e("没有其他的");
                onShareLitener.onError();
                return;
            }
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
            onShareLitener.onError();
        }


    }

    /**
     *  通过intent 方式分享到微信好友（内容）
     *  这里需要说明下，经过百度查找加测试发现，如果单纯的通过intent分享内容给微信好友
     *      1.分享文字
     *      2.分享图片
     *      3.两者不能同时分享（如果可以同时的话，麻烦告诉我下谢谢了）
     */
    public static void shareWXdesc( String share_word) {
        try{
            Intent intentFriend = new Intent();
            ComponentName compFriend = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intentFriend.setComponent(compFriend);
            intentFriend.setAction(Intent.ACTION_SEND);
            intentFriend.setType("image/*");
            intentFriend.putExtra(Intent.EXTRA_TEXT, share_word);
            intentFriend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            APP.getContext().startActivity(intentFriend);
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }

    }

    /**
     * 通过intent 方式分享内容到微信好友
     * @param imageUri
     */
    public static void shareWXImage( String imageUri) {
        try{
            Intent intentFriend = new Intent();
            ComponentName compFriend = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intentFriend.setComponent(compFriend);
            intentFriend.setAction(Intent.ACTION_SEND);
            intentFriend.setType("image/*");
            intentFriend.putExtra(Intent.EXTRA_STREAM, imageUri);
            intentFriend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            APP.getContext().startActivity(intentFriend);
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }

    }


    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }



    /**
     * 说说方式分享到qq
     */
    public static void shareQQ(String shareContent) {
        Intent intentQQFriend = new Intent();
        ComponentName componentFirend = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        intentQQFriend.setComponent(componentFirend);
        intentQQFriend.setAction(Intent.ACTION_SEND);
        intentQQFriend.setType("text/*");
        intentQQFriend.putExtra(Intent.EXTRA_TEXT,shareContent);
        intentQQFriend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        APP.getContext().startActivity(intentQQFriend);
    }


    /**
     *  通过rxjava重构分享部分的代码，这里是简写，在真实项目中，可能根据需求的不同要嵌套好几层回调，所以改成rxjava来写
     * @param weakReference
     * @param appId
     * @param packageName
     * @param shareTitle
     * @param shareContent
     * @param shareUrl
     * @param type
     * @param shareBitmap
     */
    public static void shareWXRX(WeakReference<Activity> weakReference, String appId, String packageName, String shareTitle, String shareContent, String shareUrl, int type, Bitmap shareBitmap) {
        LogUtil.e("shareWX_______");
        Bitmap localBitmap2 = Bitmap.createScaledBitmap(shareBitmap, 150, 150, true);
        if (shareBitmap != null) {
            shareBitmap.recycle();
            shareBitmap = null;
        }
        //拼接参数还是用原生的sdk来弄
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = shareUrl;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
        localWXMediaMessage.title = shareTitle;
        localWXMediaMessage.description = shareContent;
        localWXMediaMessage.thumbData = (bmpToByteArray(localBitmap2, true));
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene = type;
        //在分享的时候不调用sdk中原有的分享代码，改调用自己的，这里需要注意不要使用新的jar包，里面有的方法已经取消了，就用我这项目里的
        WxShare.sendReq(weakReference, localReq, appId, packageName);
    }

    public static String[] shareWXReadyRx(String shareImage) {
        String[] strings = new String[3];
        if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQ)) {
            LogUtil.e("安装了QQ");
            strings[0] = Constant.WEIXINAPPKEYQQ;
            strings[1] = Constant.WEIXINAPPPACKAGEQQ;
            strings[2] = shareImage;

            return strings;

        } else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEUC)) {
            LogUtil.e("安装了uc");
            strings[0] = Constant.WEIXINAPPKEYUC;
            strings[1] = Constant.WEIXINAPPPACKAGEUC;
            strings[2] = shareImage;
            return strings;

        } else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQBROWSER)) {
            strings[0] = Constant.WEIXINAPPKEYQQBROWSER;
            strings[1] = Constant.WEIXINAPPPACKAGEQQBROWSER;
            strings[2] = shareImage;
            LogUtil.e("安装了qqBrowser");
            return strings;

        } else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGENEWSTODAY)) {
            strings[0] = Constant.WEIXINAPPKEYNEWSTODAY;
            strings[1] = Constant.WEIXINAPPPACKAGENEWSTODAY;
            strings[2] = shareImage;
            LogUtil.e("安装了今日头条");
            return strings;

        } else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEBAIDU)) {
            strings[0] = Constant.WEIXINAPPKEYBAIDU;
            strings[1] = Constant.WEIXINAPPPACKAGEBAIDU;
            strings[2] = shareImage;
            LogUtil.e("安装了百度");
            return strings;
        } else if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGESINA)) {
            strings[0] = Constant.WEIXINAPPKEYSINA;
            strings[1] = Constant.WEIXINAPPPACKAGESINA;
            strings[2] = shareImage;
            LogUtil.e("安装了sina");
            return strings;
        }
        return null;
    }



    /**
     * 通过rxjava获取url图片
     *
     * @param shareImage
     * @return
     */
    public static Bitmap getRxHttpBitmap(String shareImage) {
        URL pictureUrl = null;
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            pictureUrl = new URL(shareImage);
            in = pictureUrl.openStream();
//            LogUtil.e("文件size:"+String.valueOf(in.available()/1024)+"k");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inSampleSize = 2;
            SoftReference<Bitmap> bitmapSoftReference = new SoftReference<>(BitmapFactory.decodeStream(in,null,options));
            bitmap = bitmapSoftReference.get();
            LogUtil.e("1111");

        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                LogUtil.e(Log.getStackTraceString(e));
            }
        }
        LogUtil.e("222");
        return bitmap;
    }

}
