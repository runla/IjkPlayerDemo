package com.seewo.videoviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.seewo.videoviewtest.widget.media.IjkVideoView;
import com.seewo.videoviewtest.widget.media.MediaPlayerCompat;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

public class MainActivity extends AppCompatActivity {
    private boolean mIsFullScreen;
    private IjkVideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mVideoView = findViewById(R.id.video_view);
        mVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
        mVideoView.setVideoPath("http://p7r9pxtrv.bkt.clouddn.com/201705766.mkv");
        mVideoView.start();
    }

    public void changeSize(View view1) {
        if (mIsFullScreen) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
            params.removeRule(RelativeLayout.CENTER_HORIZONTAL);
            params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            mVideoView.setLayoutParams(params);
        } else {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
            params.width = 400;
            params.height = 225;
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mVideoView.setLayoutParams(params);
        }
        mIsFullScreen = !mIsFullScreen;
    }

    @Override
    protected void onDestroy() {
        mVideoView.stopPlayback();
        super.onDestroy();
    }
    private Boolean isYuanchan = true;

    public void changeTrace(View view){
        ITrackInfo[] trackInfos = mVideoView.getTrackInfo();
        Log.e("text", "changeTrace: "+"===============", null);

//        mVideoView.showMediaInfo();
        if (isYuanchan) {
            new MyThread(2).run();
//            mVideoView.selectTrack(2);
        }else{
//            mVideoView.selectTrack(1);
            new MyThread(1).run();
        }
        isYuanchan = !isYuanchan;
        Log.e("text", "changeTrace: "+"===============", null);

    }
    private class MyThread implements Runnable{
        int type;
        public MyThread(int type){
            this.type = type;
        }
        @Override
        public void run() {
            mVideoView.selectTrack(type);
        }
    }
}
