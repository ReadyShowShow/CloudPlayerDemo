package com.cloud.player;

import android.support.multidex.MultiDexApplication;

import com.youku.cloud.player.PlayerApplication;

/**
 * 直接引用sdkInner时,直接初始化,无需加载额外内容
 *
 * @author jian
 */
public class CloudPlayerApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        new PlayerApplication(this).init();
    }
}
