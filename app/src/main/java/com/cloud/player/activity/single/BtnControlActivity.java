package com.cloud.player.activity.single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cloud.player.VideoQuality;
import com.cloud.player.R;

public class BtnControlActivity extends BaseSinglePlayerNormalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_control);
        initPlayerContainer();
        initPlayer();

        autoPlay();
    }

    private void autoPlay() {
        Intent intent = getIntent();
        String vId = intent.getStringExtra("vId");
        String vPassword = intent.getStringExtra("vPassword");
        player.playVideo(vId, vPassword, VideoQuality.VIDEO_HD.toInt());
    }

    public void setBackButtonVisible(View view) {
        boolean visible = player.isBackButtonVisible();
        player.setBackButtonVisible(!visible);
    }

    public void setFullScreenButtonVisible(View view) {
        boolean visible = player.isFullScreenButtonVisible();
        player.setFullScreenButtonVisible(!visible);
    }

    public void setControlBarVisible(View view) {
        boolean visible = player.isControlVisible();
        player.setControlVisible(!visible);
    }

    public void setFullScreen(View view) {
        boolean isFull = player.isFullScreen();
        player.setFullScreen(!isFull);
    }
}
