package com.example.player;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

public class MyVideoPlayer extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {
    private ImageView PlayOrPause;
    private SurfaceView surfaceView;
    private MediaPlayer player;
    private SeekBar seekBar;
    private LinearLayout controlLl;
    private TextView startTime, endTime;
    private SurfaceHolder holder;
    private boolean isShow = false;
    int SECOND = 300;

    public static final int UPDATE_TIME = 1;
    public static final int HIDE_CONTROL = 2;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video_player);
        setTitle("VideoPlayer");

        init();
    }

    private void init() {
        PlayOrPause = findViewById(R.id.playOrPause);
        surfaceView = findViewById(R.id.surfaceView);
        seekBar = findViewById(R.id.tv_progress);
        RelativeLayout rootViewRl = findViewById(R.id.root_rl);
        controlLl = findViewById(R.id.control_ll);
        startTime = findViewById(R.id.tv_start_time);
        endTime = findViewById(R.id.tv_end_time);
        ImageView forwardButton = findViewById(R.id.tv_forward);
        ImageView backwardButton = findViewById(R.id.tv_backward);

        surfaceView.setZOrderOnTop(false);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(new PlayerCallBack());

        player = new MediaPlayer();
        try {
            //        player.setOnCompletionListener(this);
//        player.setOnErrorListener(this);
//        player.setOnInfoListener(this);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    startTime.setText(stringForTime(mediaPlayer.getCurrentPosition()));
                    endTime.setText(stringForTime(mediaPlayer.getDuration()));
                    seekBar.setMax(mediaPlayer.getDuration());
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            });

            player.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                    changeVideoSize(mediaPlayer);
                }
            });
            String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
            //String path = "android.resource://" + this.getPackageName() + "/" + R.raw.bytedance;
            player.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayOrPause.setOnClickListener(this);
        rootViewRl.setOnClickListener(this);
        rootViewRl.setOnTouchListener(this);
        forwardButton.setOnClickListener(this);
        backwardButton.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(player != null && b) {
                    player.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_TIME:
                    updateTime();
                    handler.sendEmptyMessageDelayed(UPDATE_TIME,500);
                case HIDE_CONTROL:
                    hideControl();
                    break;
            }
        }
    };

    private void showControl() {
        if (isShow) {
            play();
        }
        isShow = true;
        handler.removeMessages(HIDE_CONTROL);
        handler.sendEmptyMessage(UPDATE_TIME);
        handler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
        controlLl.animate().setDuration(300).translationY(0);
    }

    private void play() {
        if (player == null) {
            return;
        }
        if (player.isPlaying()) {
            player.pause();
            handler.removeMessages(UPDATE_TIME);
            handler.removeMessages(HIDE_CONTROL);
            PlayOrPause.setVisibility(View.VISIBLE);
            PlayOrPause.setImageResource(android.R.drawable.ic_media_play);
        }
        else {
            player.start();
            handler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
            handler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
            PlayOrPause.setVisibility(View.INVISIBLE);
            PlayOrPause.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    private void hideControl() {
        isShow = false;
        handler.removeMessages(UPDATE_TIME);
        controlLl.animate().setDuration(300).translationY(controlLl.getHeight());
    }

    private void updateTime() {
        startTime.setText(stringForTime(player.getCurrentPosition()));
        seekBar.setProgress(player.getCurrentPosition());
    }

    private void backWard() {
        if(player != null) {
            int position = player.getCurrentPosition();
            if(position > SECOND) {
                position -= SECOND;
            }
            else {
                position = 0;
            }
            player.seekTo(position);
        }
    }

    private void forWard() {
        if(player != null) {
            int position = player.getCurrentPosition();
            position += SECOND;
            player.seekTo(position);
        }
    }

    //将长度转换为时间
    StringBuilder mFormatBuilder = new StringBuilder();
    Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    //将长度转换为时间
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        }
        else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public void changeVideoSize(MediaPlayer mediaPlayer) {
        int surfaceWidth = surfaceView.getWidth();
        int surfaceHeight = surfaceView.getHeight();

        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();

        //根据视频尺寸去计算->视频可以在sufaceView中放大的最大倍数。
        float max;
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //竖屏模式下按视频宽度计算放大倍数值
            max = Math.max((float) videoWidth / (float) surfaceWidth, (float) videoHeight / (float) surfaceHeight);
        } else {
            //横屏模式下按视频高度计算放大倍数值
            max = Math.max(((float) videoWidth / (float) surfaceHeight), (float) videoHeight / (float) surfaceWidth);
        }

        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
        videoWidth = (int) Math.ceil((float) videoWidth / max);
        videoHeight = (int) Math.ceil((float) videoHeight / max);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        surfaceView.setLayoutParams(new LinearLayout.LayoutParams(videoWidth, videoHeight));
    }

    private class PlayerCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_backward:
                backWard();
                break;
            case R.id.tv_forward:
                forWard();
                break;
            case R.id.playOrPause:
                play();
                break;
            case R.id.root_rl:
                showControl();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}