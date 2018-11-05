package com.cloud.player.activity.multi;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.youku.cloud.player.YoukuPlayer;

import java.util.HashSet;
import java.util.Set;

/**
 * 多个播放器的使用场景较少
 *
 * @author jian
 */
public abstract class BaseMultiPlayerActivity extends Activity {

    private Set<YoukuPlayer> players = new HashSet<>();

    public void addPlayerContext(YoukuPlayer player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void removePlayerContext(YoukuPlayer player) {
        if (players.contains(player)) {
            players.remove(player);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (YoukuPlayer player : players) {
            player.onCreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (YoukuPlayer player : players) {
            player.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (YoukuPlayer player : players) {
            player.onResume();
        }
    }

    @Override
    protected void onPause() {
        for (YoukuPlayer player : players) {
            player.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        for (YoukuPlayer player : players) {
            player.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        for (YoukuPlayer player : players) {
            player.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        for (YoukuPlayer player : players) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        for (YoukuPlayer player : players) {
            player.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    public void onBackPressed() {
        YoukuPlayer fullScreenPlayer = null;
        for (YoukuPlayer player : players) {
            if (player.isFullScreen()) {
                fullScreenPlayer = player;
                break;
            }
        }
        if (fullScreenPlayer != null) {
            fullScreenPlayer.setFullScreen(false);
        } else {
            finish();
        }
        super.onBackPressed();
    }
}
