package com.cloud.player.activity.single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.youku.cloud.module.VideoInfo;
import com.youku.cloud.player.PlayerListener;
import com.cloud.player.R;
import com.cloud.player.VideoQuality;

/**
 * 基本用例,正常播放器
 *
 * @author jian
 */
public class SampleActivity extends BaseSinglePlayerNormalActivity {
    private EditText playerStatusEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        playerStatusEt = (EditText) findViewById(R.id.player_status_et);
        initPlayerContainer();
        initPlayer();

        initViewsClickAction();
    }

    @Override
    protected void initPlayer() {
        super.initPlayer();
        player.addPlayerListener(new MyPlayerListener());
    }

    private void initViewsClickAction() {
        findViewById(R.id.play_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String vId = intent.getStringExtra("vId");
                String vPassword = intent.getStringExtra("vPassword");
                boolean isDownloadFile = intent.getBooleanExtra("downloadFile", false);
                if (isDownloadFile) {
                    player.playLocalVideo(vId, vPassword);
                } else {
                    player.playVideo(vId, vPassword, VideoQuality.VIDEO_HD.toInt());
                }
            }
        });
        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });
        findViewById(R.id.pause_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });
        findViewById(R.id.replay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                player.replay();
            }
        });
    }

    private class MyPlayerListener extends PlayerListener {

        private int lastPrintTime;
        private StringBuffer sb = new StringBuffer("L开始输出日志");

        @Override
        public void onAdVideoStart() {
            playerStatusEtAppendPlayerLog("onAdVideoStart");
        }

        @Override
        public void onRealVideoStart() {
            playerStatusEtAppendPlayerLog("onRealVideoStart");
        }

        @Override
        public void onPlayerPause() {
            playerStatusEtAppendPlayerLog("onPlayerPause");
        }

        @Override
        public void onPlayerStart() {
            playerStatusEtAppendPlayerLog("onPlayerStart");
        }

        @Override
        public void onCurrentPositionChanged(int millis) {
            // 10秒输出一次
            if (millis - lastPrintTime >= 10000) {
                lastPrintTime = millis;
                playerStatusEtAppendPlayerLog("onCurrentPositionChanged:" + millis);
            }
        }

        @Override
        public void onSeekComplete() {
            playerStatusEtAppendPlayerLog("onSeekComplete");
        }

        @Override
        public void onComplete() {
            playerStatusEtAppendPlayerLog("onComplete");
        }

        @Override
        public void onVideoQualityChanged() {
            playerStatusEtAppendPlayerLog("onVideoQualityChanged");
        }

        @Override
        public void onVideoInfoGot(VideoInfo videoInfo) {
            playerStatusEtAppendPlayerLog("onVideoInfoGot:" + videoInfo.toString());
        }

        @Override
        public void onVideoNeedPassword(int code) {
            playerStatusEtAppendPlayerLog("onVideoNeedPassword:" + code);
        }

        @Override
        public void onVideoSizeChanged(int width, int height) {
            playerStatusEtAppendPlayerLog("onVideoSizeChanged:width:" + width + "height:" + height);
        }

        @Override
        public void onBackButtonPressed() {
            playerStatusEtAppendPlayerLog("onBackButtonPressed");
        }

        @Override
        public void onControlVisibilityChange(boolean isShow) {
            playerStatusEtAppendPlayerLog("onControlVisibilityChange:" + isShow);
        }

        @Override
        public void onScreenModeChanged(boolean isFullScreen) {
            playerStatusEtAppendPlayerLog("onScreenModeChanged:" + isFullScreen);
        }

        @Override
        public void onError(int code, String info) {
            playerStatusEtAppendPlayerLog("onError:code:" + code + " info:" + info);
        }

        private void playerStatusEtAppendPlayerLog(String msg) {
            sb.insert(0, "\n");
            sb.insert(0, msg);
            playerStatusEt.setText(sb);
        }
    }

}

