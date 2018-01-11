# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\android\newSdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------



#-------------------------------------------------------------------------
# x5
#-optimizationpasses 7
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize
-dontusemixedcaseclassnames
-verbose
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontwarn dalvik.**
#-overloadaggressively

#@proguard_debug_start
# ------------------ Keep LineNumbers and properties ---------------- #
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-renamesourcefileattribute TbsSdkJava
-keepattributes SourceFile,LineNumberTable
#@proguard_debug_end

# --------------------------------------------------------------------------
# Addidional for x5.sdk classes for apps

-keep class com.tencent.smtt.export.external.**{
    *;
}

-keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {
	*;
}

-keep class com.tencent.smtt.sdk.CacheManager {
	public *;
}

-keep class com.tencent.smtt.sdk.CookieManager {
	public *;
}

-keep class com.tencent.smtt.sdk.WebHistoryItem {
	public *;
}

-keep class com.tencent.smtt.sdk.WebViewDatabase {
	public *;
}

-keep class com.tencent.smtt.sdk.WebBackForwardList {
	public *;
}

-keep public class com.tencent.smtt.sdk.WebView {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
	public static final <fields>;
	public java.lang.String getExtra();
	public int getType();
}

-keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$PictureListener {
	public <fields>;
	public <methods>;
}


-keepattributes InnerClasses

-keep public enum com.tencent.smtt.sdk.WebSettings$** {
    *;
}

-keep public enum com.tencent.smtt.sdk.QbSdk$** {
    *;
}

-keep public class com.tencent.smtt.sdk.WebSettings {
    public *;
}


-keepattributes Signature
-keep public class com.tencent.smtt.sdk.ValueCallback {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebViewClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient$FileChooserParams {
	public <fields>;
	public <methods>;
}

-keep class com.tencent.smtt.sdk.SystemWebChromeClient{
	public *;
}
# 1. extension interfaces should be apparent
-keep public class com.tencent.smtt.export.external.extension.interfaces.* {
	public protected *;
}

# 2. interfaces should be apparent
-keep public class com.tencent.smtt.export.external.interfaces.* {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.WebViewCallbackClient {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.WebStorage$QuotaUpdater {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebIconDatabase {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebStorage {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk$PreInitCallback {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.Tbs* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.LogFileUtils {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLog {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLogClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}

# Added for game demos
-keep public class com.tencent.smtt.sdk.TBSGamePlayer {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClient* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClientExtension {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerService* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.Apn {
	public <fields>;
	public <methods>;
}
# end


-keep public class com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension {
	public <fields>;
	public <methods>;
}

-keep class MTT.ThirdAppInfoNew {
	*;
}

-keep class com.tencent.mtt.MttTraceEvent {
	*;
}

# Game related
-keep public class com.tencent.smtt.gamesdk.* {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBooter {
        public <fields>;
        public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivity {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivityProxy {
	public protected *;
}

-keep public class com.tencent.smtt.gamesdk.internal.TBSGameServiceClient {
	public *;
}
#---------------------------------------------------------------------------


#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Application{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}
#----------------------------x5 结束

#---------------------------------2.第三方包-------------------------------


 ### greenDAO 3
 -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
 public static java.lang.String TABLENAME;
 }
 -keep class **$Properties
 -dontwarn org.greenrobot.greendao.database.**


-keep class com.lidroid.** { *; }

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#gson

-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# 使用Gson时需要配置Gson的解析对象及变量都不混淆。不然Gson会找不到变量。
# 将下面替换成自己的实体类
-keep class com.example.a51425.mainuiframe.bean.** { *; }


# picasso
-dontwarn com.squareup.okhttp.**

#eventBus
#==================protobuf======================
#-dontwarn com.google.**
#-keep class com.google.protobuf.** {*;}

##eventBus
#-keep class de.greenrobot.event.** {*;}
#-keepclassmembers class ** {
#    public void onEvent*(**);
#    void onEvent*(**);
#}


   #-----butterknife-----
    -keep class butterknife.** { *; }
    -dontwarn butterknife.internal.**
    -keep class **$$ViewBinder { *; }

    -keepclasseswithmembernames class * {
        @butterknife.* <fields>;
    }

    -keepclasseswithmembernames class * {
        @butterknife.* <methods>;
    }
#    # banner 的混淆代码
#    -keep class com.youth.banner.** {
#        *;
#     }
     #okgo
     #okgo
     -dontwarn com.lzy.okgo.**
     -keep class com.lzy.okgo.**{*;}

     #okhttp
     -dontwarn okhttp3.**
     -keep class okhttp3.**{*;}
     #okio
     -dontwarn okio.**
     -keep class okio.**{*;}

##-- 微信sdk--
#-keep class com.tencent.mm.opensdk.** {
#   *;
#}
#
#-keep class com.tencent.wxop.** {
#   *;
#}
#
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
##----rxjava
#-dontwarn sun.misc.**
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#   long producerIndex;
#   long consumerIndex;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode producerNode;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode consumerNode;
#}

## rx
-dontwarn rx.**
-keepclassmembers class rx.** { *; }

##---极光推送
#-dontoptimize
#-dontpreverify
#
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}


################ autobahn ###############
-keep class de.tavendo.autobahn.** { *; }

################ ImmersionBar ###############

 -keep class com.gyf.barlibrary.* {*;}

##eventBus
#-keep class de.greenrobot.event.** {*;}
#-keepclassmembers class ** {
#    public void onEvent*(**);
#    void onEvent*(**);
#}

##有盟统计
#-keepclassmembers class * {
#   public <init> (org.json.JSONObject);
#}
#-keep public class [yueqian].R$*{
#public static final int *;
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#-dontusemixedcaseclassnames
#    -dontshrink
#    -dontoptimize
#    -dontwarn com.google.android.maps.**
#    -dontwarn android.webkit.WebView
#    -dontwarn com.umeng.**
#    -dontwarn com.tencent.weibo.sdk.**
#    -dontwarn com.facebook.**
#    -keep public class javax.**
#    -keep public class android.webkit.**
#    -dontwarn android.support.v4.**
#    -keep enum com.facebook.**
#    -keepattributes Exceptions,InnerClasses,Signature
#    -keepattributes *Annotation*
#    -keepattributes SourceFile,LineNumberTable
#
#    -keep public interface com.facebook.**
#    -keep public interface com.tencent.**
#    -keep public interface com.umeng.socialize.**
#    -keep public interface com.umeng.socialize.sensor.**
#    -keep public interface com.umeng.scrshot.**
#    -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
#    -keep public class com.umeng.socialize.* {*;}
#
#
#    -keep class com.facebook.**
#    -keep class com.facebook.** { *; }
#    -keep class com.umeng.scrshot.**
#    -keep public class com.tencent.** {*;}
#    -keep class com.umeng.socialize.sensor.**
#    -keep class com.umeng.socialize.handler.**
#    -keep class com.umeng.socialize.handler.*
#    -keep class com.umeng.weixin.handler.**
#    -keep class com.umeng.weixin.handler.*
#    -keep class com.umeng.qq.handler.**
#    -keep class com.umeng.qq.handler.*
#    -keep class UMMoreHandler{*;}
#    -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
#    -keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#    -keep class im.yixin.sdk.api.YXMessage {*;}
#    -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
#    -keep class com.tencent.mm.sdk.** {
#     *;
#    }
#    -keep class com.tencent.mm.opensdk.** {
#   *;
#    }
#    -dontwarn twitter4j.**
#    -keep class twitter4j.** { *; }
#
#    -keep class com.tencent.** {*;}
#    -dontwarn com.tencent.**
#    -keep public class com.umeng.com.umeng.soexample.R$*{
#    public static final int *;
#    }
#    -keep public class com.linkedin.android.mobilesdk.R$*{
#    public static final int *;
#        }
#    -keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#    }
#
#    -keep class com.tencent.open.TDialog$*
#    -keep class com.tencent.open.TDialog$* {*;}
#    -keep class com.tencent.open.PKDialog
#    -keep class com.tencent.open.PKDialog {*;}
#    -keep class com.tencent.open.PKDialog$*
#    -keep class com.tencent.open.PKDialog$* {*;}
#
#    -keep class com.sina.** {*;}
#    -dontwarn com.sina.**
#    -keep class  com.alipay.share.sdk.** {
#       *;
#    }
#    -keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#    }
#
#    -keep class com.linkedin.** { *; }
#    -keepattributes Signature
#

##百度地图
#-keep class com.baidu.** { *; }
# -keep class vi.com.gdi.bgl.android.**{*;}
#
# ### greenDAO 3
# -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
# public static java.lang.String TABLENAME;
# }
# -keep class **$Properties
# -dontwarn org.greenrobot.greendao.database.**
#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------
#4.反射相关的类和方法
#-keep class com.xx.xx.xx.xx.view.** { *; }
#-keep class com.xx.xx.xx.xx.xx.** { *; }


#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------
#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
#-printmapping proguardMapping.txt

-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-verbose
-printmapping mapping.txt
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}


#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}


#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class com.kogame.god.thing.creature.**  {
*;
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#----------------------------------------------------------------------------