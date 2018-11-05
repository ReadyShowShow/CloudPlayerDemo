package com.cloud.player.activity.single;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.youku.cloud.player.PlayerListener;
import com.cloud.player.R;

public abstract class BaseSinglePlayerNormalActivity extends BaseSinglePlayerActivity {
    protected ViewGroup mPlayerContainerViewNormal;
    protected ViewGroup mPlayerContainerViewFull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initPlayerContainer() {
        mPlayerContainerViewNormal = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_normal);

        mPlayerContainerViewFull = new FrameLayout(this);
        mPlayerContainerViewFull.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewGroup) getWindow().getDecorView()).addView(mPlayerContainerViewFull);
    }

    protected void initPlayer() {
        player.setDisplayContainer(mPlayerContainerViewNormal);
        player.addPlayerListener(new MyPlayerListener());
    }

    protected class MyPlayerListener extends PlayerListener {
        @Override
        public void onBackButtonPressed() {
            onBackPressed();
        }

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
