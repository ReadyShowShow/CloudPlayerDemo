package com.cloud.player.activity.multi;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.youku.cloud.player.PlayerListener;
import com.cloud.player.R;
import com.youku.cloud.player.YoukuPlayer;

public class MultiPlayerActivity extends BaseMultiPlayerActivity {
    private ViewGroup containerSmall;
    private ViewGroup containerNormal;
    private ViewGroup containerFull;

    private String vId;
    private String vPassword;

    private YoukuPlayer playerSmall;
    private YoukuPlayer playerNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_multi_player);

        containerSmall = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_small);
        containerNormal = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal);
        containerFull = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_full);

        playerSmall = new YoukuPlayer(this);
        addPlayerContext(playerSmall);
        playerSmall.setDisplayContainer(containerSmall);
        playerSmall.setBackButtonVisible(false);
        playerSmall.setFullScreenButtonVisible(false);
        playerSmall.addPlayerListener(new MyPlayerListener());
        playerSmall.playVideo("XMjY3MTQ2MDE0OA==", null, 2);

        playerNormal = new YoukuPlayer(this);
        addPlayerContext(playerNormal);
        playerNormal.setDisplayContainer(containerNormal);
        playerNormal.setBackButtonVisible(false);
        playerNormal.setFullScreenButtonVisible(false);
        playerNormal.addPlayerListener(new MyPlayerListener() {
            @Override
            public void onScreenModeChanged(boolean isFullScreen) {
                if (isFullScreen) {
                    playerNormal.setDisplayContainer(containerFull);
                } else {
                    playerNormal.setDisplayContainer(containerSmall);
                }
            }
        });
        playerNormal.playVideo(vId, vPassword, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removePlayerContext(playerSmall);
        removePlayerContext(playerNormal);
    }

    private void initData() {
        Intent intent = getIntent();
        vId = intent.getStringExtra("vId");
        vPassword = intent.getStringExtra("vPassword");
    }


    private class MyPlayerListener extends PlayerListener {
        @Override
        public void onBackButtonPressed() {
            onBackPressed();
        }
    }
}
