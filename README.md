𓀀𓀁𓀂𓀃𓀄𓀅𓀆𓀇𓀈𓀉𓀊𓀋𓀌𓀍𓀎𓀏𓀐𓀑𓀒𓀓𓀔𓀕𓀖𓀗𓀘𓀙𓀚𓀛𓀜𓀝𓀞𓀟𓀠𓀡𓀢𓀣𓀤𓀥𓀦𓀧𓀨𓀩𓀪𓀫𓀬𓀻𓀼𓀽𓀾𓀿𓁀𓁁𓁂𓁃𓁄𓁅𓁆𓁇𓁈𓁉  
视频云<http://cloud.youku.com/>  
视频云Demo for Android <https://github.com/ReadyShowShow/CloudPlayerDemo>
# 引入SDK

## gradle配置

添加仓库
``` gradle
    repositories {
        maven { url 'https://dl.bintray.com/readyshow/CloudPlayer' }
    }
```

引入依赖
``` gradle
dependencies {
    ......
    implementation('com.youku.cloud.player:CloudPlayerSDK:7.0.1@aar')
}
```

过滤掉多余so文件,控制apk大小为10M左右
```
android {
    ......
    defaultConfig {
        ......
        ndk {
            abiFilters "armeabi-v7a"
        }
    }
}
```

## 混淆事项

proguard-rules-youku.pro 将此混淆文件加入proguardFiles
```gradle
proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro', 'proguard-rules-youku.pro'
```

# 调用说明

## 必须的初始化

在Application中完成初始化
关于CLIENT_ID与CLIENT_SECRET的申请, 参见<http://cloud.youku.com/>
```java
public class CloudPlayerApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        new PlayerApplication(this).init();
        YoukuProfile.CLIENT_ID = 你申请的CLIENT_ID;
        YoukuProfile.CLIENT_SECRET = 你申请的CLIENT_SECRET;
    }
}
```

## 播放

### 播放器的创建, 并关联Activity的生命周期

需要创建一个Player:`new YoukuPlayer(ctx);`
然后关联Activity的生命周期.

```java
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.youku.cloud.player.YoukuPlayer;

public abstract class BaseSinglePlayerActivity extends AppCompatActivity {
    public static String TAG = "PlayerBaseActivity";
    protected YoukuPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = new YoukuPlayer(this);
        player.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onResume();
    }

    @Override
    protected void onPause() {
        player.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        player.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        player.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        player.onConfigurationChanged(newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        player.onWindowFocusChanged(hasFocus);
    }

    /**
     * 用户点击返回键:
     * 如果是全屏的话,先竖屏.
     * 如果是竖屏,则直接finish
     */
    @Override
    public void onBackPressed() {
        if (player.isFullScreen()) {
            player.setFullScreen(false);
        } else {
            finish();
        }
    }
}
```

### 为Player指定播放的容器

任意的ViewGroup均可作为播放容器.
```java
// 屏幕旋转时, 改变播放器的容器
@Override
public void onScreenModeChanged(boolean isFullScreen) {
    if (isFullScreen) {
        player.setDisplayContainer((ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_full));
    } else {
        player.setDisplayContainer((ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal));
    }
}
```

### 调用播放器播放视频

```java
int quality = VideoQuality.VIDEO_STANDARD.toInt()
player.playVideo(vId, vPassword, quality);
```

## 状态监听

### 响应全屏事件, 横竖屏的切换

播放器的容器,可以是任意形状,任意位置.
播放器本身并没有全屏时的播放容器, 需要指出全屏状态下的播放容器, 如果不指出, 则不会全屏.
```java
public abstract class BaseSinglePlayerNormalActivity extends BaseSinglePlayerActivity {
    protected void initPlayer() {
        player.addPlayerListener(new MyPlayerListener());
    }

    protected class MyPlayerListener extends PlayerListener {
        @Override
        public void onScreenModeChanged(boolean isFullScreen) {
            if (isFullScreen) {
                player.setDisplayContainer(mPlayerContainerViewFull);
            } else {
                player.setDisplayContainer(mPlayerContainerViewNormal);
            }
        }
    }
}
```

### 响应返回事件, 点击播放器左上角返回按钮的监听

```java

public abstract class BaseSinglePlayerNormalActivity extends BaseSinglePlayerActivity {
    protected void initPlayer() {
        player.addPlayerListener(new MyPlayerListener());
    }

    protected class MyPlayerListener extends PlayerListener {
        @Override
        public void onBackButtonPressed() {
            onBackPressed();
        }
    }

    /**
     * 用户点击返回键:
     * 如果是全屏的话,先竖屏.
     * 如果是竖屏,则直接finish
     */
    @Override
    public void onBackPressed() {
        if (player.isFullScreen()) {
            player.setFullScreen(false);
        } else {
            finish();
        }
    }
}
```

### 其他事件的监听

看PlayerListener类定义, 见名知义.

```java
public class SampleActivity extends BaseSinglePlayerNormalActivity {
    @Override
    protected void initPlayer() {
        super.initPlayer();
        player.addPlayerListener(new MyPlayerListener());
    }
    private class MyPlayerListener extends PlayerListener {
        public PlayerListener() {
        }

        /**
         * 视频开始加载
         */
        public void onLoadingStart() {
        }

        /**
         * 视频结束加载
         */
        public void onLoadingEnd() {
        }

        /**
         * 广告开始
         */
        public void onAdVideoStart() {
        }

        /**
         * 广告播完，正片开始播放
         */
        public void onRealVideoStart() {
        }

        public void onPlayerStart() {
        }

        public void onPlayerPause() {
        }

        /**
         * 播放器进度更新
         */
        public void onCurrentPositionChanged(int millisecond) {
        }

        /**
         * seek完成
         */
        public void onSeekComplete() {
        }

        /**
         * 播放完成
         */
        public void onComplete() {
        }

        /**
         * 播放器清晰度变化
         */
        public void onVideoQualityChanged() {
        }

        /**
         * 获取到视频信息
         */
        public void onVideoInfoGot(VideoInfo videoInfo) {
        }

        /**
         * 需要密码
         *
         * @param code 0需要密码，1密码错误
         */
        public void onVideoNeedPassword(int code) {
        }

        /**
         * 视频原片大小发生变化
         */
        public void onVideoSizeChanged(int width, int height) {
        }

        /**
         * 播放器左上角返回键
         */
        public void onBackButtonPressed() {
        }

        /**
         * 控制条是否可见
         */
        public void onControlVisibilityChange(boolean isShow) {
        }

        /**
         * 是否全屏的切换
         */
        public void onScreenModeChanged(boolean isFullScreen) {
        }

        public void onError(int code, String info) {
        }
    }
}

```

### 播放器的控制

看函数名, 见名知义.
```java

public class YoukuPlayer {
    public void setDisplayContainer(ViewGroup container) {
    }

    public void addPlayerListener(PlayerListener listener) {
    }

    public void removePlayerListener(PlayerListener listener) {
    }

    public void playLocalVideo(String vId, String vPassword) {
    }

    public void playVideo(String vId, String vPassword) {
    }

    public void playVideo(String vId, String vPassword, int definition) {
    }

    public void pause() {
    }

    public void start() {
    }

    public void stop() {
    }

    public void release() {
    }

    public void replay() {
    }

    public void seekTo(int millisecond) {
    }

    public int getCurrentPosition() {
    }

    public boolean isLockScreen() {
    }

    public void setLockScreen(boolean isLock) {
    }

    public boolean isFullScreen() {
    }

    public void setFullScreen(boolean isFull) {
    }

    public void setFullScreenButtonVisible(boolean visible) {
    }

    public boolean isFullScreenButtonVisible() {
    }

    public void setBackButtonVisible(boolean visible) {
    }

    public boolean isBackButtonVisible() {
    }

    public void setControlVisible(boolean visible) {
    }

    public boolean isControlVisible() {
    }

    public int getVideoWidth() {
    }

    public int getVideoHeight() {
    }
}
```

