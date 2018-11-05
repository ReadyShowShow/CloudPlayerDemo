package com.cloud.player.activity;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cloud.player.R;
import com.cloud.player.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class VidItemAdapter extends BaseAdapter {
    Context context;
    List<VidItem> vidItems;

    public VidItemAdapter(Context context) {
        this.context = context;
        // 读取SD卡的配置
        File file = new File(Environment.getExternalStorageDirectory(), "video_id_password");
        String s = FileUtil.file2String(file);
        List<VidItem> itemsFromFile = JSON.parseArray(s, VidItem.class);
        if (itemsFromFile != null) {
            this.vidItems = itemsFromFile;
        } else {
            vidItems = new ArrayList<>();
            vidItems.add(new VidItem("空", ""));
            vidItems.add(new VidItem("优酷普通", "XMTM2MDQ5MzUxMg=="));
            vidItems.add(new VidItem("优酷无水印", "XMTU1MjQ0MTcyOA=="));
            vidItems.add(new VidItem("优酷加密", "XNzU3OTg0NzAw", "123456"));
            vidItems.add(new VidItem("加密可下载", "XNzU0NjE4MDgw", "123456"));
            vidItems.add(new VidItem("私密托管", "XMTc2ODg2Mzk0MA=="));
            vidItems.add(new VidItem("优酷会员", "XMTc0NDYzNDYwNA=="));
            vidItems.add(new VidItem("优酷版权", "XMTY5NDg2MzY5Ng=="));
            vidItems.add(new VidItem("优酷付费", "XMTczOTQ0MzY3Ng=="));
            vidItems.add(new VidItem("优酷4:3", "XMTEyMDIwNDI0"));
            vidItems.add(new VidItem("动画", "XMTU4MzkyNDcxMg=="));
            vidItems.add(new VidItem("易车", "XNTgzODA2MTI0"));
            vidItems.add(new VidItem("订阅可看", "XMTU0ODY1OTYwOA=="));
            vidItems.add(new VidItem("mi4无法播放", "XMjY1MTQxMTQyNA=="));
        }
    }

    @Override
    public int getCount() {
        return vidItems.size();
    }

    @Override
    public VidItem getItem(int position) {
        return vidItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
        TextView itemView = (TextView) convertView.findViewById(R.id.textView);
        VidItem vidItem = getItem(position);
        itemView.setText(vidItem.desc + " : " + vidItem.vid);
        return convertView;
    }

    public static class VidItem implements java.io.Serializable {
        public String desc;
        public String vid;
        public String password;

        public VidItem() {
        }

        public VidItem(String desc, String vid) {
            this.desc = desc;
            this.vid = vid;
        }

        public VidItem(String desc, String vid, String password) {
            this.desc = desc;
            this.vid = vid;
            this.password = password;
        }
    }
}
