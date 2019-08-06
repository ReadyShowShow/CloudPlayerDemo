#####################视频云的混淆配置#####################

# 泛型不可混淆,fastJSON在处理list问题时,存在泛型的转换
-keepattributes Signature

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-keep class * implements android.os.Parcelable {*;}
-keep class * implements java.io.Serializable {*;}
-keep class * implements java.lang.Runnable {*;}
-keep class * implements java.lang.Cloneable {*;}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep @com.youku.kubus.NoProguard class * {*;}
-keep,allowobfuscation @interface com.youku.kubus.NoProguard
-keepclassmembers class * {  com.youku.kubus.NoProguard *; }
-keepattributes *Annotation* 
-keepclassmembers class ** { @com.youku.kubus.Subscribe public <methods>; }

-keep class * implements mtopsdk.mtop.domain.IMTOPDataObject {*;}
-keep class * implements mtopsdk.mtop.global.init.IMtopInitTask {*;}

-keep class javax.** {*;}
-dontwarn javax.**
-keep class com.youku** {*;}
-dontwarn com.youku**
-keep class com.alibaba** {*;}
-dontwarn com.alibaba**
-keep class com.taobao** {*;}
-dontwarn com.taobao**
-keep class com.ut** {*;}
-dontwarn com.ut**
-keep class com.securityguard** {*;}
-keep class com.luajava** {*;}
-keep class com.intertrust** {*;}
-keep class yunos.media** {*;}
-keep class android.taobao** {*;}
-dontwarn android.taobao**
-keep class com.tmalltv**{*;}
-dontwarn com.tmalltv**
-keep class com.yunos**{*;}
-dontwarn com.yunos**
-keep class mtopsdk.common**{*;}
-dontwarn mtopsdk.common**
-keep class mtopsdk.mtop**{*;}
-dontwarn mtopsdk.mtop**
-keep class mtopsdk.network**{*;}
-dontwarn mtopsdk.network**
-keep class org.android**{*;}
-dontwarn org.android**
-keep class com.uploader** {*;}
-dontwarn com.uploader**
-keep class members.** {*;}
-dontwarn members.**
-keep class org.codehaus** {*;}
-dontwarn org.codehaus**
