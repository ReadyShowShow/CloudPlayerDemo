package com.cloud.player.activity.download;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youku.cloud.download.DownLoadManager;
import com.cloud.player.R;
import com.youku.cloud.utils.ValidateUtil;
import com.youku.download.DownInfo;

class DownloadListAdapter extends BaseAdapter {
    private Context context;

    public DownloadListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return DownLoadManager.getInstance().getDownInfos().size();
    }

    @Override
    public DownInfo getItem(int position) {
        return (DownInfo) DownLoadManager.getInstance().getDownInfos().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.download_list_item, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.initViews(convertView);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        DownInfo dditem = (DownInfo) DownLoadManager.getInstance().getDownInfos().get(position);
        holder.downNameTv.setText(dditem.getName());
        holder.downProgressPb.setProgress((int) Math.round(dditem.getProgress() * 100));
        holder.downProgressTv.setText((int) Math.round(dditem.getProgress() * 100) + "%");
        holder.downSpeedTv.setText(dditem.getSpeed() + "Kbps");
        final DownInfo info = getItem(position);
        holder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownInfo dInfo = DownLoadManager.getInstance().getDownInfoByVid(info.getVid());
                if (ValidateUtil.isValid(dInfo)) {
                    if (dInfo.isIsstop()) {
                        if (DownLoadManager.getInstance().startDownLoad(info.getVid())) {
                            v.setEnabled(false);
                            ((Button) v).setText("开始ing");
                        }
                    } else {
                        if (DownLoadManager.getInstance().stopDownLoad(info.getVid())) {
                            v.setEnabled(false);
                            ((Button) v).setText("停止ing");
                        }
                    }
                }
            }
        });
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadManager.getInstance().deleteDownLoad(info.getVid());
            }
        });
        if (dditem.isOperating()) {
            // 按钮正在操作，应该禁用按钮，防止反复点击
            holder.startBtn.setEnabled(false);
        } else {
            if (dditem.isIsstop()) {
                holder.startBtn.setText("开始");
            } else {
                holder.startBtn.setText("停止");
            }
            holder.startBtn.setEnabled(true);
        }
        if (dditem.isDone()) {
            holder.startBtn.setText("完成");
            holder.startBtn.setEnabled(false);
            holder.downProgressPb.setProgress(100);
            holder.downProgressTv.setText(100 + "%");
            holder.downSpeedTv.setText(0 + "Kbps");
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView downNameTv;
        public ProgressBar downProgressPb;
        public TextView downProgressTv;
        public TextView downSpeedTv;
        public Button startBtn;
        public Button delBtn;

        public void initViews(View convertView) {
            downNameTv = (TextView) convertView.findViewById(R.id.down_name);
            downProgressPb = (ProgressBar) convertView.findViewById(R.id.down_progress);
            downProgressTv = (TextView) convertView.findViewById(R.id.down_p_txt);
            downSpeedTv = (TextView) convertView.findViewById(R.id.down_s_txt);
            startBtn = (Button) convertView.findViewById(R.id.btn_start);
            delBtn = (Button) convertView.findViewById(R.id.btn_del);
        }
    }
}
