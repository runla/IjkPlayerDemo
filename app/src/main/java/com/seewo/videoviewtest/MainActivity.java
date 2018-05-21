package com.seewo.videoviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.seewo.videoviewtest.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

public class MainActivity extends AppCompatActivity{
    private boolean mIsFullScreen;
    private IjkVideoView mVideoView;
    private static final String TAG = "MainActivity";
    private String url = "http://p7r9pxtrv.bkt.clouddn.com/201705766.mkv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mVideoView = findViewById(R.id.video_view);
        mVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
        startVideo();
//        mVideoView.setVideoPath("http://p7r9pxtrv.bkt.clouddn.com/201705766.mkv");
//        mVideoView.start();
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

    private void startVideo() {

        mVideoView.setVideoPath(url);
        mVideoView.start();
    }

    @Override
    protected void onDestroy() {
        mVideoView.stopPlayback();
        super.onDestroy();
    }
    private Boolean isYuanchan = true;
    private int position;
    public void changeTrace(View view){
        ITrackInfo[] trackInfos = mVideoView.getTrackInfo();
        Log.e("text", "changeTrace: "+"===============", null);
        position = mVideoView.getCurrentPosition();
        if (isYuanchan) {
            mVideoView.selectTrack(2);
//            mVideoView.seekTo(position);
        }else{
            mVideoView.selectTrack(1);
//            mVideoView.seekTo(position);
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
