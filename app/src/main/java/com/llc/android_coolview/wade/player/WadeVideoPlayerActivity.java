package com.llc.android_coolview.wade.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.llc.android_coolview.R;


/**
 * Created by liulongchao on 2015/11/11.
 */
public class WadeVideoPlayerActivity extends AppCompatActivity {

    private static final String TAG = WadeVideoPlayerActivity.class.getSimpleName();
    private VideoView mVideoView;
    private EditText mPath;
    private ImageView mPlay;
    private ImageView mPause;
    private ImageView mReset;
    private ImageView mStop;

    private File DLTempFile;
    private TextView playedTextView = null;
    private TextView downtext = null;

    private boolean iserror = false;

    private int VideoDuraton = 1;
    private int curPosition;
    private int mediaLength = 1;
    private int totalKbRead = 0;
    private SeekBar seekbar = null;
    private double downper = 0.00;
    private double playper = 0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_wade_video_player);
        findViews();
        init();
    }

    private void findViews() {
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mPath = (EditText) findViewById(R.id.path);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        playedTextView = (TextView) findViewById(R.id.has_played);
        downtext = (TextView) findViewById(R.id.downtext);
        mPlay = (ImageView) findViewById(R.id.play);
        mPause = (ImageView) findViewById(R.id.pause);
        mReset = (ImageView) findViewById(R.id.reset);
        mStop = (ImageView) findViewById(R.id.stop);
    }

    private void init() {
        seekbar.setMax(100);
        mPath.setText("http://v.qq.com/page/h/3/5/h0018d1ai35.html");
        mVideoView.setOnPreparedListener(prepareListener);
        mVideoView.setOnCompletionListener(CompletionListener);
        mVideoView.setOnErrorListener(ErrorListener);

        final String localPath=Environment.getExternalStorageDirectory().getAbsolutePath()+ "/VideoCache/" + "Fast.and.the.Furious1" + ".mp4";
        MediaController controller=new MediaController(this);
        controller.setAnchorView(mVideoView);
        mVideoView.setMediaController(controller);
        Uri parseUri=Uri.parse(localPath);
        mVideoView.setVideoURI(parseUri);
        mVideoView.start();

        mPlay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(WadeVideoPlayerActivity.this, WadePlayingActivity.class);
                intent.putExtra("url", mPath.getText().toString());
                intent.putExtra("cache",localPath);
                startActivity(intent);
            }
        });

        mPause.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mVideoView.isPlaying() && mVideoView.canPause()) {
                    mVideoView.pause();
                }
            }
        });

        mReset.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mVideoView != null) {
                    mVideoView.stopPlayback();
                    if(DLTempFile!=null&&DLTempFile.exists())
                        DLTempFile.delete();
                }
            }
        });

        mStop.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mVideoView.isPlaying()) {
                    mVideoView.stopPlayback();
                }
            }
        });

    }

    private MediaPlayer.OnPreparedListener prepareListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            Log.d(TAG, "OnPreparedListener");
            myHandler.sendEmptyMessage(VIDEO_STATE_UPDATE);
            mVideoView.seekTo(curPosition);
            mp.start();
        }
    };

    private MediaPlayer.OnCompletionListener CompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer arg0) {
            Log.d(TAG, "OnCompletionListener");
            curPosition = 0;
            mVideoView.stopPlayback();
        }
    };

    private MediaPlayer.OnErrorListener ErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            iserror = true;
            mVideoView.stopPlayback();
            return true;
        }
    };

    private final static int VIDEO_STATE_UPDATE = 0;
    private final static int CACHE_VIDEO_READY = 1;
    private final static int CACHE_VIDEO_UPDATE = 2;
    private final static int CACHE_VIDEO_END = 3;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VIDEO_STATE_UPDATE:
                    Log.d(TAG, "VIDEO_STATE_UPDATE");
                    downper = totalKbRead * 100.00 / mediaLength * 1.0;
                    seekbar.setSecondaryProgress((int) downper);
                    downtext.setText(String.format("[%.4f]", downper));
                    if (mVideoView.isPlaying()) {
                        VideoDuraton = mVideoView.getDuration() + 1;
                        curPosition = mVideoView.getCurrentPosition();
                        playper = curPosition * 100.00 / VideoDuraton * 1.0;
                        seekbar.setProgress((int) playper);
                        int i = curPosition;
                        i /= 1000;
                        int minute = i / 60;
                        int hour = minute / 60;
                        int second = i % 60;
                        minute %= 60;
                        playedTextView.setText(String.format("%02d:%02d:%02d[%.4f][%.4f]", hour, minute, second,playper, downper));
                    }
                    myHandler.sendEmptyMessageDelayed(VIDEO_STATE_UPDATE, 1000);
                    break;

                case CACHE_VIDEO_READY:
                    Log.d(TAG, "CACHE_VIDEO_READY");
                    mVideoView.setVideoPath(DLTempFile.getAbsolutePath());
                    mVideoView.start();
                    break;

                case CACHE_VIDEO_UPDATE:
                    Log.d(TAG, "CACHE_VIDEO_UPDATE");

                    if (iserror) {
                        mVideoView.setVideoPath(DLTempFile.getAbsolutePath());
                        mVideoView.start();
                        iserror = false;
                    }
                    break;

                case CACHE_VIDEO_END:
                    Log.d(TAG, "CACHE_VIDEO_END");

                    if (iserror) {
                        mVideoView.setVideoPath(DLTempFile.getAbsolutePath());
                        mVideoView.start();
                        iserror = false;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
