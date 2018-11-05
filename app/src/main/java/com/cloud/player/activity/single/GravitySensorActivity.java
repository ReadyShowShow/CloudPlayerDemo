package com.cloud.player.activity.single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cloud.player.VideoQuality;
import com.cloud.player.R;

public class GravitySensorActivity extends BaseSinglePlayerNormalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity_sensor);
        initPlayerContainer();
        initPlayer();

        autoPlay();
    }

    @Override
    protected void initPlayerContainer() {
        mPlayerContainerViewNormal = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal);
        mPlayerContainerViewFull = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_full);
    }

    private void autoPlay() {
        Intent intent = getIntent();
        String vId = intent.getStringExtra("vId");
        String vPassword = intent.getStringExtra("vPassword");
        player.playVideo(vId, vPassword, VideoQuality.VIDEO_HD.toInt());
    }

    public void lockScreen(View view) {
        boolean isLock = player.isLockScreen();
        player.setLockScreen(!isLock);
    }
}
