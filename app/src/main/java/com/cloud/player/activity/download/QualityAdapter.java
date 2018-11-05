package com.cloud.player.activity.download;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cloud.player.R;
import com.cloud.player.VideoQuality;

import java.util.ArrayList;

class QualityAdapter extends BaseAdapter {
    private DownloadActivity downloadActivity;
    ArrayList<VideoQuality> qualities = new ArrayList<>();

    public QualityAdapter(DownloadActivity downloadActivity) {
        this.downloadActivity = downloadActivity;
        qualities.add(VideoQuality.VIDEO_STANDARD);
        qualities.add(VideoQuality.VIDEO_HD);
        qualities.add(VideoQuality.VIDEO_HD2);
    }

    @Override
    public int getCount() {
        return qualities.size();
    }

    @Override
    public VideoQuality getItem(int position) {
        return qualities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(downloadActivity).inflate(R.layout.spinner_item, null);
        TextView itemView = (TextView) convertView.findViewById(R.id.textView);
        itemView.setText(getItem(position).chiName);
        return convertView;
    }
}
