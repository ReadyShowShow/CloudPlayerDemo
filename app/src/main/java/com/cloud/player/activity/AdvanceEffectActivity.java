package com.cloud.player.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cloud.player.R;
import com.cloud.player.activity.multi.MultiPlayerActivity;
import com.cloud.player.activity.single.AutoResizeActivity;
import com.cloud.player.activity.single.BtnControlActivity;
import com.cloud.player.activity.single.FloatWindowActivity;
import com.cloud.player.activity.single.GravitySensorActivity;
import com.cloud.player.activity.single.ListSinglePlayerActivity;
import com.cloud.player.activity.single.MovePlayerActivity;
import com.cloud.player.activity.single.MovePlayerOnFullScreenActivity;

public class AdvanceEffectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_effect);
    }

    public void autoWindow(View view) {
        startActivityWithData(AutoResizeActivity.class);
    }

    public void controlPlayer(View view) {
        startActivityWithData(BtnControlActivity.class);
    }

    public void movePlayer(View view) {
        startActivityWithData(MovePlayerActivity.class);
    }

    public void movePlayerOnFull(View view) {
        startActivityWithData(MovePlayerOnFullScreenActivity.class);
    }

    public void floatWindow(View view) {
        startActivityWithData(FloatWindowActivity.class);
    }

    public void gravitySensor(View view) {
        startActivityWithData(GravitySensorActivity.class);
    }

    public void gotoMultiPlayerActivity(View view) {
        startActivityWithData(MultiPlayerActivity.class);
    }

    public void gotoListSinglePlayerActivity(View view) {
        startActivityWithData(ListSinglePlayerActivity.class);
    }

    private void startActivityWithData(Class clazz) {
        Intent intent = getIntent();
        String vId = intent.getStringExtra("vId");
        String vPassword = intent.getStringExtra("vPassword");

        Intent i = new Intent(this, clazz);
        i.putExtra("vId", vId);
        i.putExtra("vPassword", vPassword);
        startActivity(i);
    }
}
