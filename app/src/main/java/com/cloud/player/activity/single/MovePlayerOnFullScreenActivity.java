package com.cloud.player.activity.single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youku.cloud.player.PlayerListener;
import com.cloud.player.R;

public class MovePlayerOnFullScreenActivity extends BaseSinglePlayerActivity {
    public static String TAG = "MovePlayerOnFullScreenActivity";
    private String vId;
    private String vPassword;

    private ViewGroup mPlayerContainerViewSmall;
    private ViewGroup[] mPlayerContainerViewFull = new ViewGroup[2];
    private View actionsOnFullScreen;
    private int currentDisplayIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initActivityViews();

        player.setDisplayContainer(mPlayerContainerViewSmall);
        player.addPlayerListener(new MyPlayerListener());
        player.playVideo(vId, vPassword, 2);
    }

    private void initData() {
        Intent intent = getIntent();
        vId = intent.getStringExtra("vId");
        vPassword = intent.getStringExtra("vPassword");
    }

    private void initActivityViews() {
        setContentView(R.layout.activity_move_player_on_full_screen);
        mPlayerContainerViewSmall = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal);

        mPlayerContainerViewFull[0] = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_full);
        mPlayerContainerViewFull[1] = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_center);

        actionsOnFullScreen = findViewById(R.id.actions_on_full_screen);
        actionsOnFullScreen.setVisibility(View.GONE);

        findViewById(R.id.full_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryFullScreen();
            }
        });
        findViewById(R.id.next_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovePlayerOnFullScreenActivity.this, "playVideo()", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyPlayerListener extends PlayerListener {
        @Override
        public void onBackButtonPressed() {
            onBackPressed();
        }

        @Override
        public void onScreenModeChanged(boolean isFullScreen) {
            if (isFullScreen) {
                currentDisplayIndex = 0;
                player.setDisplayContainer(mPlayerContainerViewFull[0]);
                actionsOnFullScreen.setVisibility(View.VISIBLE);
            } else {
                player.setDisplayContainer(mPlayerContainerViewSmall);
                actionsOnFullScreen.setVisibility(View.GONE);
            }
        }

        private boolean onControlShowFirstTime = true;

        @Override
        public void onControlVisibilityChange(boolean isShow) {
            if (isShow) {
                if (onControlShowFirstTime) {
                    onControlShowFirstTime = false;
                    return;
                }
                onPlayerTaped();
            }
        }
    }

    private void onPlayerTaped() {
        boolean fullScreen = player.isFullScreen();
        if (fullScreen) {
            int nextIndex = (++currentDisplayIndex) % mPlayerContainerViewFull.length;
            player.setDisplayContainer(mPlayerContainerViewFull[nextIndex]);
            currentDisplayIndex = nextIndex;
        }
    }

    private void tryFullScreen() {
        boolean fullScreen = player.isFullScreen();
        if (fullScreen) {
            currentDisplayIndex = 0;
            player.setDisplayContainer(mPlayerContainerViewFull[0]);
        }
    }
}
