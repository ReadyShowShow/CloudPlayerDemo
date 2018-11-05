package com.cloud.player.activity.single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cloud.player.VideoQuality;
import com.youku.cloud.player.PlayerListener;
import com.cloud.player.R;

public class FloatWindowActivity extends BaseSinglePlayerActivity {
    private String vId;
    private String vPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_float_window);

        player.setDisplayContainer((ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal));
        player.playVideo(vId, vPassword, VideoQuality.VIDEO_STANDARD.toInt());
        player.addPlayerListener(new MyPlayerListener());
    }

    private void initData() {
        Intent intent = getIntent();
        vId = intent.getStringExtra("vId");
        vPassword = intent.getStringExtra("vPassword");
    }

    public void floatWindow(View view) {
        player.setDisplayContainer((ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_float));
    }


    private class MyPlayerListener extends PlayerListener {
        @Override
        public void onBackButtonPressed() {
            onBackPressed();
        }

        @Override
        public void onScreenModeChanged(boolean isFullScreen) {
            if (isFullScreen) {
                player.setDisplayContainer((ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_full));
            } else {
                player.setDisplayContainer((ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal));
            }
        }
    }
}
