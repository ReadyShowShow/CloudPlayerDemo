package com.cloud.player.activity.single;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.youku.cloud.player.YoukuPlayer;

/**
 * 单播放器即可实现各种效果
 *
 * @author jian 1210105886@qq.com
 */
public abstract class BaseSinglePlayerActivity extends AppCompatActivity {
    public static String TAG = "PlayerBaseActivity";
    protected YoukuPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = new YoukuPlayer(this);
        player.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onResume();
    }

    @Override
    protected void onPause() {
        player.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        player.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        player.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        player.onConfigurationChanged(newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        player.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        if (player.isFullScreen()) {
            player.setFullScreen(false);
        } else {
            finish();
        }
    }

}
