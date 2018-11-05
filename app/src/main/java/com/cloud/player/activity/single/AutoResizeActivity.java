package com.cloud.player.activity.single;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;

import com.cloud.player.R;
import com.cloud.player.VideoQuality;
import com.cloud.player.widget.ResizeAbleView;
import com.youku.cloud.player.PlayerListener;

/**
 * @author jian
 */
public class AutoResizeActivity extends BaseSinglePlayerNormalActivity {
    OrientationEventListener mOrientationListener;
    ResizeAbleView rav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_resize_window);
        initPlayerContainer();
        rav = (ResizeAbleView) mPlayerContainerViewNormal;
        initPlayer();

        // 切换视频时,监听视频大小的改变,并及时设置长宽比
        player.addPlayerListener(new PlayerListener() {
            @Override
            public void onVideoSizeChanged(int width, int height) {
                if (width > 0 && height > 0) {
                    rav.setWidthHeightRate(width / (float) height);
                }
            }
        });

        // 手机转屏时,监听手机方向,并及时设置长宽比
        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                int width = player.getVideoWidth();
                int height = player.getVideoHeight();
                if (width > 0 && height > 0) {
                    rav.setWidthHeightRate(width / (float) height);
                }
            }
        };
        mOrientationListener.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }

    private String[] vids = {"XMTEyMDIwNDI0", "XMTM2MDQ5MzUxMg==", "XMzExMDg4MDQ3Ng"};
    private int vidIndex = 0;

    public void nextVideo(View view) {
        String nextVid = vids[(vidIndex++) % vids.length];
        player.playVideo(nextVid, null);
    }
}
