package com.cyxk.wrframelibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.cyxk.wrframelibrary.config.ConfigUtil;
import com.cyxk.wrframelibrary.framework.CallBackListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * File
 */
public class FileUtil {

    private static File file;
    private static boolean flag;


    private static String saveBitmap( String fileName, Bitmap bitmap) {

        File file = makeFile(fileName);
        if (file == null) {
            return "";
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (Exception e) {
            LogUtil.e("error1 ---" + Log.getStackTraceString(e));
        }
        try {
            bitmap.compress(CompressFormat.JPEG, 90, fOut);
        } catch (Exception e) {
            LogUtil.e("error2 ---" + Log.getStackTraceString(e));
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }


    public static File makeFile(String fileName) {
            String photoFile = createPhotoFile();
            File file = new File(photoFile, fileName);
            return file;
    }


    /**
     * 通过路径生产对应的文件
     *
     * @param realPathFromURI
     */
    public static File makeFileFromPath(String realPathFromURI) {
        File file1 = new File(realPathFromURI);
        File fileParent1 = file1.getParentFile();
        if (!fileParent1.exists()) {
            fileParent1.mkdirs();
        }
        return file1;
    }


    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        flag = false;
        file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            LogUtil.e("不存在 ____________");
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                LogUtil.e("删除文件 ____________");
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                LogUtil.e("删除目录 ____________");
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        flag = false;
        file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象，File类型
     * @param newName 新文件的文件名，String类型
     * @return 重命名成功则返回true
     */
    public static String renameTo(File oldFile, String newName) {
        File newFile = new File(oldFile.getParentFile() + File.separator + newName);
        boolean flag = oldFile.renameTo(newFile);

        return newFile.getAbsolutePath();
    }

    public static void download(final String _url, final String path, final CallBackListener<String> callBackListener) throws Exception {

        Observable.just(_url)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String _url) {
                        InputStream is = null;
                        OutputStream os = null;
                        try {
                            URL url = new URL(_url);
                            // 打开连接
                            URLConnection con = url.openConnection();
                            //设置请求超时为5s
                            con.setConnectTimeout(5 * 1000);
                            // 输入流
                            is = con.getInputStream();

                            // 1K的数据缓冲
                            byte[] bs = new byte[1024];
                            // 读取到的数据长度
                            int len;
                            // 输出的文件流
                            File sf = new File(path);

                            os = new FileOutputStream(sf);
                            // 开始读取
                            while ((len = is.read(bs)) != -1) {
                                os.write(bs, 0, len);
                            }
                            // 完毕，关闭所有链接

                            return sf.getAbsolutePath();
                        } catch (Exception e) {
                            LogUtil.e(Log.getStackTraceString(e));
                            callBackListener.onFailure();
                            return null;
                        } finally {
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(Log.getStackTraceString(e));
                        callBackListener.onFailure();
                    }

                    @Override
                    public void onNext(String path) {
                        if (!TextUtils.isEmpty(path)) {
                            callBackListener.onSuccess(path);
                        }

                    }
                });
    }




    /**
     * 将byte字节转换为十六进制字符串
     *
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }


    /**
     * 把字节数组保存为一个文件
     * @Author HEH
     * @EditTime 2010-07-19 上午11:45:56
     */
    public static File getFileFromBytes(byte[] bfile, String outputFile) {

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(outputFile);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * 图片路径
     */
    public static String createPhotoFile() {
        String path = "";
        // 判断sd卡是否存在
        try{
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {
                File sdDir = Environment.getExternalStorageDirectory();// 获取根目录
                File childDir = new File(sdDir + "/" + ConfigUtil.DOWNLOAD_PHOTO);
                if (!childDir.exists()) {
                    childDir.mkdirs();
                }
                path = childDir.getAbsolutePath();
            }
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }

        return path;
    }

}
