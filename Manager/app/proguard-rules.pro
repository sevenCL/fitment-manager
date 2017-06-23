# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\android\adt-bundle-windows-x86_64-20140624/tools/proguard/proguard-android.txt
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
-dontwarn
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-dontpreverify
-keepattributes SourceFile,LineNumberTable
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.app.Fragment

-ignorewarning

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# fastjson
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**

-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature

-keep public class * implements java.io.Serializable {
        public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


#web
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class com.xiuz.manager.web.WebInterface {
  public *;
}

################### region for xUtils
-keepattributes Signature,*Annotation*
-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    @org.xutils.view.annotation.Event <methods>;
}
#################### end region

#个推
#-dontwarn com.igexin.**
#-keep class com.igexin.**{*;}

#umeng
#-keepclassmembers class * {
#   public <init>(org.json.JSONObject);
#}
#
#-keep class com.umeng*.**{*;}
#
#-keep public class com.xiuz.manager.R$*{
#    public static final int *;
#}
#-keep class com.umeng.onlineconfig.OnlineConfigAgent {
#        public <fields>;
#        public <methods>;
#}
#
#-keep class com.umeng.onlineconfig.OnlineConfigLog {
#        public <fields>;
#        public <methods>;
#}
#
#-keep interface com.umeng.onlineconfig.UmengOnlineConfigureListener {
#        public <methods>;
#}

#百度
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

-keep class org.apache.**{*;}

#极光
#-dontoptimize
#-dontpreverify
#
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }

#二维码
#-dontwarn com.google.zxing.**
#-keep  class com.google.zxing.**{*;}
#==================gson==========================
#-dontwarn com.google.**
#-keep class com.google.gson.** {*;}

#==================protobuf======================
#-dontwarn com.google.**
#-keep class com.google.protobuf.** {*;}

#支付宝
#-libraryjars libs/alipaySDK-20150602.jar
#
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}

#银联
#-keep  public class com.unionpay.uppay.net.HttpConnection {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.net.HttpParameters {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.BankCardInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.PAAInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.ResponseInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.PurchaseInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.util.DeviceInfo {
#	public <methods>;
#}
#-keep  public class java.util.HashMap {
#	public <methods>;
#}
#-keep  public class java.lang.String {
#	public <methods>;
#}
#-keep  public class java.util.List {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.util.PayEngine {
#	public <methods>;
#	native <methods>;
#}
#
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
#-keep  public class com.unionpay.utils.UPUtils {
#	native <methods>;
#}