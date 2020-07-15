package com.example.player;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

public class Video extends AppCompatActivity {
    private VideoView mVideoView;
    private Button btnPlay, btnPause;
    MediaController mMediaController;
    String uri = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTitle("VideoPlayer");

        mVideoView = new VideoView(this);
        mVideoView = (VideoView) findViewById(R.id.video);
        mVideoView.setVideoURI(Uri.parse(uri));
        //mVideoView.setVideoPath(getVideoPath(R.raw.bytedance));
        mMediaController = new MediaController(this);
        btnPlay = (Button) findViewById(R.id.btn_Play);
        btnPause = (Button) findViewById(R.id.btn_Pause);
        btnPlay.setOnClickListener(new mClick());
        btnPause.setOnClickListener(new mClick());
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mMediaController.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(mMediaController);
            if (view == btnPlay) {
                mVideoView.start();
            } else if (view == btnPause) {
                mVideoView.pause();
            }
        }
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}