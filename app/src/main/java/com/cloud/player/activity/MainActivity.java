package com.cloud.player.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.cloud.player.R;
import com.cloud.player.activity.download.DownloadActivity;
import com.cloud.player.activity.single.SampleActivity;
import com.youku.cloud.player.YoukuProfile;


/**
 * 主界面,配置测试数据
 *
 * @author jian
 */
public class MainActivity extends AppCompatActivity {
    private EditText vIdEt;
    private EditText vPasswordEt;
    private EditText cIdEt;
    private EditText cSecEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void gotoSamplePlayer(View view) {
        startActivityWithData(SampleActivity.class);
    }

    public void gotoAdvanceEffect(View view) {
        startActivityWithData(AdvanceEffectActivity.class);
    }

    public void gotoDownload(View view) {
        startActivityWithData(DownloadActivity.class);
    }

    private void startActivityWithData(Class clazz) {
        Intent i = new Intent(this, clazz);
        i.putExtra("vId", vIdEt.getText().toString().trim());
        i.putExtra("vPassword", vPasswordEt.getText().toString().trim());

        YoukuProfile.CLIENT_ID = cIdEt.getText().toString().trim();
        YoukuProfile.CLIENT_SECRET = cSecEt.getText().toString().trim();
        startActivity(i);
    }

    private void initView() {
        vIdEt = (EditText) this.findViewById(R.id.vid);
        vPasswordEt = (EditText) this.findViewById(R.id.et_password);
        cIdEt = (EditText) findViewById(R.id.client_id);
        cSecEt = (EditText) findViewById(R.id.client_secret);

        String defaultVid = "XMTgzOTgzODYwMA==";
        vIdEt.setText(defaultVid);

        final AppCompatSpinner vidSelector = (AppCompatSpinner) findViewById(R.id.youkuvid);
        vidSelector.setAdapter(new VidItemAdapter(this));
        vidSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VidItemAdapter.VidItem selectItem = (VidItemAdapter.VidItem) vidSelector.getSelectedItem();
                vIdEt.setText(selectItem.vid);
                vPasswordEt.setText(selectItem.password);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final AppCompatSpinner cidSelector = (AppCompatSpinner) findViewById(R.id.youku_client);
        cidSelector.setAdapter(new ClientItemAdapter(this));
        cidSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClientItemAdapter.ClientItem selectItem = (ClientItemAdapter.ClientItem) cidSelector.getSelectedItem();
                cIdEt.setText(selectItem.cid);
                cSecEt.setText(selectItem.secret);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    //
    //
    // 动态获取权限
    //
    //
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
