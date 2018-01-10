package com.cyxk.wrframelibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;


/**
 * Created by XinYue on 2016/7/12.
 * <p/>
 * 调取相机或者调取相册
 */
public class PhotoUtils {
    public static final String IMAGE_FILE_VIDEO = "video.mov";//相机录像地址
    protected Activity context;
    public static final String IMAGE_FILE_NAME = "linyooImage/avatarImage.jpg";// 头像文件名称
    protected static final int REQUESTCODE_PICK = 0;        // 相册选图标记
    protected static final int REQUESTCODE_TAKE = 1;        // 相机拍照标记
    private byte[] bytes;
    private Bitmap bitmap2;

    public PhotoUtils(Activity context) {
        this.context = context;
        // check Android 6.0 permission

    }

    /*
    * 从相机获取
    */
    public void openCiname() {
//        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //指定调用相机拍照后的照片存储的路径
//        File fileParent1 = FileUtil.makeFile(context,IMAGE_FILE_NAME);
//        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileParent1));
//        context.startActivityForResult(takeIntent, REQUESTCODE_TAKE);
    }

    /*
    * 从相机获取录像
    */
   public void openCinameVideo() {
//
//        Intent intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//       File fileParent1 = FileUtil.makeFile(context,IMAGE_FILE_VIDEO);
//      intentVideo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileParent1));
//        intentVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);//限制录制时间10秒
//       context.startActivityForResult(intentVideo, 10);

    }


    /*
    * 从相册获取
    */
    public void openGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(pickIntent, REQUESTCODE_PICK);
    }

    /**
     * 打开相机
     *
     * @param num 返回吊起传入的参数
     */
    public void openCiname(int num) {
//        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //指定调用相机拍照后的照片存储的路径
//        File fileParent1 = FileUtil.makeFile(context,IMAGE_FILE_NAME);
//        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileParent1));
//        context.startActivityForResult(takeIntent, num);
    }


    /**
     * 打开相册
     *
     * @param num 返回吊起传入的参数
     */
    public void openGallery(int num) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(pickIntent, num + 100);
    }






    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
