package com.cloud.player.activity.download;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.youku.cloud.download.DownLoadManager;
import com.cloud.player.R;
import com.cloud.player.VideoQuality;
import com.cloud.player.activity.single.SampleActivity;
import com.youku.download.DownInfo;
import com.youku.download.DownLoaderListener;

import java.io.File;

public class DownloadActivity extends AppCompatActivity {
    private String vId;
    private String vPassword;
    private AppCompatSpinner videoQualitySp;
    private DownloadListAdapter downAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_download);
        initQualityListView();
        initDownloadListView();
        DownLoadManager.getInstance().setDownLoaderListener(new MyDownListener());
    }

    private void initData() {
        Intent intent = getIntent();
        vId = intent.getStringExtra("vId");
        vPassword = intent.getStringExtra("vPassword");
    }

    private void initQualityListView() {
        videoQualitySp = (AppCompatSpinner) findViewById(R.id.cloud_qua);
        videoQualitySp.setAdapter(new QualityAdapter(this));
    }

    private void initDownloadListView() {
        ListView downloadListLv = (ListView) findViewById(R.id.download_state_list_lv);
        downloadListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DownInfo downInfo = (DownInfo) parent.getAdapter().getItem(position);
                if (!downInfo.isDone()) {
                    Toast.makeText(getBaseContext(), "未下载完毕", Toast.LENGTH_SHORT).show();
                    return;
                }
                String path = downInfo.getM3u8Path();
                File file = new File(path);
                if (!file.exists()) {
                    Toast.makeText(getBaseContext(), "文件已被删除", Toast.LENGTH_SHORT).show();
                    DownLoadManager.getInstance().deleteDownLoad(downInfo.getVid());
                    return;
                }
                Intent intent = new Intent(getBaseContext(), SampleActivity.class);
                intent.putExtra("vId", downInfo.getVid());
                intent.putExtra("downloadFile", true);
                startActivity(intent);
            }
        });
        downAdapter = new DownloadListAdapter(this);
        downloadListLv.setAdapter(downAdapter);
    }

    public void addDownloadItem(View view) {
        VideoQuality def = (VideoQuality) videoQualitySp.getSelectedItem();
        if (TextUtils.isEmpty(vId)) {
            Toast.makeText(getBaseContext(), "vid为空", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean b = DownLoadManager.getInstance().addNewTask(vId, vPassword, "", def.toInt());
        if (b) {
            Toast.makeText(getBaseContext(), "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "添加失败，重复添加", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyDownListener extends DownLoaderListener {
        @Override
        public void onDelete(String vid) {
            super.onDelete(vid);
            downAdapter.notifyDataSetChanged();
        }

        @Override
        public void onTimeout(String vid) {
            super.onTimeout(vid);
            downAdapter.notifyDataSetChanged();
        }

        @Override
        public void onStart(String vid) {
            super.onStart(vid);
            // String cover = DownLoadManager.getInstance().getDownInfoByVid(cid).getCoverImage()
            downAdapter.notifyDataSetChanged();
        }

        @Override
        public void onError(String vid, int errorcode, Exception e) {
            super.onError(vid, errorcode, e);
            downAdapter.notifyDataSetChanged();
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinshed(String vid) {
            super.onFinshed(vid);
            downAdapter.notifyDataSetChanged();
        }

        @Override
        public void onProgress(String vid, double progress, double speed) {
            super.onProgress(vid, progress, speed);
            downAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(String vid) {
            super.onCancelled(vid);
            downAdapter.notifyDataSetChanged();
        }
    }
}
