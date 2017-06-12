package com.example.a51425.mainuiframe.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.R;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * 像图片框架，网络框架最好不要直接使用，否则当你发现原先的框架不能解决你现在的问题时，就会有很大的麻烦！
 */
public class ImageUtils {


    public static void glideLoader(Context context, Object  url, int erroImg, int emptyImg, ImageView iv) {

        //原生 API
        if (context==null){
            context = APP.getContext();
        }
        Glide.with(context).load(url)
                .error(R.mipmap.ic_launcher)
                .into(iv);
//        Picasso.with(context)
//                    .load(""+url)
//                    .placeholder(R.drawable.bg_default_main_one_big)
//                    .error(R.drawable.bg_default_main_one_big)
//                    .into(iv);
    }


    /**
     * 默认使用appContext的
     * @param url
     * @param iv
     * @param tag
     */
    public static void newGlideLoader(Object url , ImageView iv, int tag) {

        if (0 == tag) {
            Glide.with(APP.getContext())
                    .load(url)
                    .bitmapTransform(new CropCircleTransformation(APP.getContext()))
                    .crossFade()
                    .into(iv);
        } else if (1 == tag) {
            Glide.with(APP.getContext())
                    .load(url)
                    .bitmapTransform(new RoundedCornersTransformation(APP.getContext(),10,0, RoundedCornersTransformation.CornerType.ALL))
                    .crossFade()
                    .into(iv);
        }else if (3==tag){
            Glide.with(APP.getContext())
                    .load(url)
                    .crossFade()
//                    .placeholder(R.drawable.bg_default_main_one_small)
                    .into(iv);
        }
    }

    /**
     * 重载可以传context
     * @param context
     * @param url
     * @param iv
     * @param tag
     */
    public static void newGlideLoader(Context context,Object url , ImageView iv, int tag) {

        if (0 == tag) {
            Glide.with(context)
                    .load(url)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .crossFade()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv);
        } else if (1 == tag) {
            Glide.with(context)
                    .load(url)
//                    .placeholder(R.drawable.mine_default_head)
                    .bitmapTransform(new RoundedCornersTransformation(context,10,0, RoundedCornersTransformation.CornerType.ALL))
//                    .override((int)x,(int)y)
                    .crossFade()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv);
        }else if (3==tag){
            Glide.with(context)
                    .load(url)
//                    .override((int)x,(int)y)
                    .crossFade()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv);
        }
    }

    /**
     * 禁止缓存
     * @param url
     * @param mShowPhoto
     */
    public static void loaderNoCache(Object url, ImageView mShowPhoto) {

        Glide.with(APP.getContext())
                .load(url)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mShowPhoto);
    }

    /**
     * 加载bitmap到ImageView
     * @param context
     * @param bitmap
     * @param iv
     */
    public static void glideLoaderBitmap(Context context, Bitmap bitmap, ImageView iv) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(APP.getContext())
                .load(bytes)
                .crossFade()
                .error(R.mipmap.ic_launcher)
                .into(iv);
    }

    /**
     * 禁止任何缓存
     */
//    public static void glideCenterCrop(Context context,Object bitmapUrl,ImageView iv){
//        Glide.with(APP.getContext())
//                .load(bitmapUrl)
//                .centerCrop()
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .error(R.drawable.default_all)
//                .into(iv);
//    }
    public static void clearCache(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(APP.getContext()).clearDiskCache();
            }
        }).start();
        Glide.get(APP.getContext()).clearMemory();
    }

}
