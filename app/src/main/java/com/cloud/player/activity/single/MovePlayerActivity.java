package com.cloud.player.activity.single;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.youku.cloud.player.PlayerListener;
import com.cloud.player.R;

public class MovePlayerActivity extends BaseSinglePlayerActivity {
    public static String TAG = "MovePlayerActivity";
    private String vId;
    private String vPassword;

    private ViewGroup[] mPlayerContainerViews = new ViewGroup[5];
    private ViewGroup mPlayerContainerViewFull;
    private int currentDisplayIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_move_player);
        initActivityViews();

        player.setDisplayContainer(mPlayerContainerViews[0]);
        player.addPlayerListener(new MyPlayerListener());
        player.playVideo(vId, vPassword, 2);

        initViewsClickAction();
    }

    private void initData() {
        Intent intent = getIntent();
        vId = intent.getStringExtra("vId");
        vPassword = intent.getStringExtra("vPassword");
    }

    private void initActivityViews() {
        mPlayerContainerViews[0] = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal);
        mPlayerContainerViews[1] = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_left_top);
        mPlayerContainerViews[2] = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_left_bottom);
        mPlayerContainerViews[3] = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_right_bottom);
        mPlayerContainerViews[4] = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_right_top);

        mPlayerContainerViewFull = (FrameLayout) findViewById(R.id.demo_cloud_screen_player_container_view_full);

        animatedContainer(mPlayerContainerViews[0]);
    }

    private void animatedContainer(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.3f);
        scaleX.setInterpolator(new DecelerateInterpolator());
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.3f);
        scaleY.setInterpolator(new DecelerateInterpolator());
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(scaleX, scaleY);
        animSet.setDuration(2000);
        animSet.start();
    }

    private void initViewsClickAction() {
        findViewById(R.id.move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextIndex = (++currentDisplayIndex) % mPlayerContainerViews.length;
                player.setDisplayContainer(mPlayerContainerViews[nextIndex]);
                currentDisplayIndex = nextIndex;
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
                player.setDisplayContainer(mPlayerContainerViewFull);
            } else {
                player.setDisplayContainer(mPlayerContainerViews[0]);
            }
        }
    }
}
