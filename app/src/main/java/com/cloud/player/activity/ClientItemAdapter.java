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

class ClientItemAdapter extends BaseAdapter {
    Context context;
    List<ClientItem> clientItems;

    public ClientItemAdapter(Context context) {
        this.context = context;
        // 读取SD卡的配置
        File file = new File(Environment.getExternalStorageDirectory(), "client_id_secret");
        String s = FileUtil.file2String(file);
        List<ClientItem> itemsFromFile = JSON.parseArray(s, ClientItem.class);
        if (itemsFromFile != null) {
            this.clientItems = itemsFromFile;
        } else {
            clientItems = new ArrayList<>();
            clientItems.add(new ClientItem("空", "", ""));
            clientItems.add(new ClientItem("普通", "792b1d08a5348d0d", "9a98ce3841ae9f686fbea940a93b8167"));
        }
    }

    @Override
    public int getCount() {
        return clientItems.size();
    }

    @Override
    public ClientItem getItem(int position) {
        return clientItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
        TextView itemView = (TextView) convertView.findViewById(R.id.textView);
        ClientItem clientItem = getItem(position);
        itemView.setText(clientItem.desc + " : " + clientItem.cid);
        return convertView;
    }

    public static class ClientItem implements java.io.Serializable {
        public String desc;
        public String cid;
        public String secret;

        public ClientItem() {
        }

        public ClientItem(String desc, String cid, String secret) {
            this.desc = desc;
            this.cid = cid;
            this.secret = secret;
        }
    }
}
