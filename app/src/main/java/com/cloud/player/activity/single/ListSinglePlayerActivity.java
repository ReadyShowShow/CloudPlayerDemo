package com.cloud.player.activity.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youku.cloud.player.PlayerListener;
import com.cloud.player.R;

import java.util.ArrayList;

public class ListSinglePlayerActivity extends BaseSinglePlayerActivity {

    private ViewGroup mPlayerContainerViewFull;
    private ViewGroup listViewLastPlayerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_single_player);
        mPlayerContainerViewFull = (ViewGroup) findViewById(R.id.demo_cloud_screen_player_container_view_full);
        ListView playerList = (ListView) findViewById(R.id.demo_cloud_video_list);
        playerList.setAdapter(new MyAdapter());
        player.addPlayerListener(new MyPlayerListener());
    }


    class MyAdapter extends BaseAdapter {
        ArrayList<PlayerCover> playerCovers = new ArrayList<>();

        public MyAdapter() {
            playerCovers.add(new PlayerCover("XMjc5NzM3MjI4OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner0.jpg"));
            playerCovers.add(new PlayerCover("XMTQ3NDAyNzExNg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner1.jpg"));
            playerCovers.add(new PlayerCover("XMTQ3NDA0NzYzMg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner2.jpg"));
            playerCovers.add(new PlayerCover("XMTQ2NjUzMTE2OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner3.jpg"));
            playerCovers.add(new PlayerCover("XMTQ4MTExNjYxNg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner4.jpg"));
            playerCovers.add(new PlayerCover("XMjc5NzgzNDg4OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner5.jpg"));
            playerCovers.add(new PlayerCover("XMTQ4MjQyNTEzNg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner0.jpg"));
            playerCovers.add(new PlayerCover("XMjgyNTgxOTA4MA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner1.jpg"));
            playerCovers.add(new PlayerCover("XMjgxMzcwMjg2MA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner2.jpg"));
            playerCovers.add(new PlayerCover("XMjM1Nzc2MTc2OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner3.jpg"));
            playerCovers.add(new PlayerCover("XMjgwMzMzMzY1Ng==", "http://7xplo7.com1.z0.glb.clouddn.com/banner4.jpg"));
            playerCovers.add(new PlayerCover("XMjgwMzM5NzczMg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner5.jpg"));
            playerCovers.add(new PlayerCover("XMjc5NzM3MjI4OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner0.jpg"));
            playerCovers.add(new PlayerCover("XMTQ3NDAyNzExNg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner1.jpg"));
            playerCovers.add(new PlayerCover("XMTQ3NDA0NzYzMg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner2.jpg"));
            playerCovers.add(new PlayerCover("XMTQ2NjUzMTE2OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner3.jpg"));
            playerCovers.add(new PlayerCover("XMTQ4MTExNjYxNg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner4.jpg"));
            playerCovers.add(new PlayerCover("XMjc5NzgzNDg4OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner5.jpg"));
            playerCovers.add(new PlayerCover("XMTQ4MjQyNTEzNg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner0.jpg"));
            playerCovers.add(new PlayerCover("XMjgyNTgxOTA4MA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner1.jpg"));
            playerCovers.add(new PlayerCover("XMjgxMzcwMjg2MA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner2.jpg"));
            playerCovers.add(new PlayerCover("XMjM1Nzc2MTc2OA==", "http://7xplo7.com1.z0.glb.clouddn.com/banner3.jpg"));
            playerCovers.add(new PlayerCover("XMjgwMzMzMzY1Ng==", "http://7xplo7.com1.z0.glb.clouddn.com/banner4.jpg"));
            playerCovers.add(new PlayerCover("XMjgwMzM5NzczMg==", "http://7xplo7.com1.z0.glb.clouddn.com/banner5.jpg"));
        }

        @Override
        public int getCount() {
            return playerCovers.size();
        }

        @Override
        public Object getItem(int position) {
            return playerCovers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ListSinglePlayerActivity.this).inflate(R.layout.list_item_video, parent, false);
                holder.init(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final PlayerCover data = (PlayerCover) getItem(position);

            final ViewHolder finalHolder = holder;
            ImageLoader.getInstance().displayImage(data.imgUrl, finalHolder.videoImage);
            finalHolder.setContainerVisibility(data.playerContainerVisibility);
            finalHolder.videoImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.stop();
                    player.release();
                    player.playVideo(data.vId, null, 2);
                    listViewLastPlayerContainer = finalHolder.playerContainer;
                    player.setDisplayContainer(finalHolder.playerContainer);
                    resetOtherData();
                    data.playerContainerVisibility = true;
                    finalHolder.setContainerVisibility(true);
                }
            });
            return convertView;
        }

        private void resetOtherData() {
            for (PlayerCover item : playerCovers) {
                item.playerContainerVisibility = false;
            }
        }

        private class ViewHolder {
            public ViewGroup playerContainer;
            public ImageView videoImage;

            public void init(View root) {
                videoImage = (ImageView) root.findViewById(R.id.list_item_video_image);
                playerContainer = (ViewGroup) root.findViewById(R.id.list_item_player_container_view);
                setContainerVisibility(false);
            }

            public void setContainerVisibility(boolean visibility) {
                playerContainer.setVisibility(visibility ? View.VISIBLE : View.GONE);
            }
        }

        private class PlayerCover {
            public String vId;
            public String imgUrl;
            public boolean playerContainerVisibility;

            public PlayerCover(String vId, String imgUrl) {
                this.vId = vId;
                this.imgUrl = imgUrl;
            }
        }
    }

    private class MyPlayerListener extends PlayerListener {
        @Override
        public void onBackButtonPressed() {
            onBackPressed();
        }

        @Override
        public void onScreenModeChanged(boolean isFullScreen) {
            if (isFullScreen) {
                player.setDisplayContainer(mPlayerContainerViewFull);
            } else {
                player.setDisplayContainer(listViewLastPlayerContainer);
            }
        }
    }
}
