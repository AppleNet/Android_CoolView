package com.llc.android_coolview.wade.player;

import android.media.MediaPlayer;
import android.os.Handler;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by liulongchao on 2015/11/11.
 */
public class AudioPlayer implements MediaPlayer.OnErrorListener,MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener {

    private MediaPlayer mPlayer;
    private String current;
    private static final int MIN_BUFF=100*1024;
    private int totalKbRead=0;
    private Handler handler=new Handler();
    private File DLTempFile;
    private File BUFFTempFile;
    private final String TEMP_DOWNLOAD_FILE_NAME = "tempMediaData";
    private final String TEMP_BUFF_FILE_NAME = "tempBufferData";
    private final String FILE_POSTFIX = ".mp4";
    private final int PER_READ = 1024;
    private boolean pause;
    private boolean stop;
    private final int UNKNOWN_LENGTH = -1;
    private Handler mHandler = null;

    private boolean downloadOver = false;
    private boolean wasPlayed = false;


    public void setHandler(Handler handler){
        mHandler=handler;
    }

    public void play(final String path){
        try {
            downloadOver=false;
            totalKbRead=0;
            if(path.equals(current)&&mPlayer!=null){
                mPlayer.start();
            }
            current=path;
            mPlayer=null;
            new PlayThread(current).start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void setListener(){
        if(mPlayer!=null){
            mPlayer.setOnErrorListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnCompletionListener(this);
        }
    }

    private void playFromNet(String url,int start,int end){
        URLConnection cn=null;
        FileOutputStream out=null;
        InputStream is=null;
        try {
            cn=new URL(url).openConnection();
            cn.connect();
            is=cn.getInputStream();
            int mediaLength=cn.getContentLength();
            if(is==null){
                return;
            }
            DLTempFile=File.createTempFile(TEMP_DOWNLOAD_FILE_NAME,FILE_POSTFIX);
            out=new FileOutputStream(DLTempFile);
            byte buf[]=new byte[PER_READ];
            int readLength=0;
            while (readLength!=-1&&!stop){
                if(pause){
                    Thread.sleep(1000);
                    continue;
                }
                readLength=is.read(buf);
                if(readLength>0){
                    out.write(buf,0,readLength);
                    totalKbRead+=readLength;
                }
                dealWithBufferData();
            }
            if (totalKbRead == mediaLength) {
                downloadOver = true;
                dealWithLastData();

                if (DLTempFile != null && DLTempFile.exists()) {
                    DLTempFile.delete();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void dealWithLastData() {
        Runnable updater = new Runnable() {
            public void run() {
                transferBufferToMediaPlayer();
            }
        };
        handler.post(updater);
    }

    private void dealWithBufferData() {
        if (mPlayer == null || !wasPlayed) {
            if (totalKbRead >= MIN_BUFF) {
                try {
                    startMediaPlayer();
                } catch (Exception e) {
                }
            }
        } else if (mPlayer.getDuration() - mPlayer.getCurrentPosition() <= 1000) {
            // deleteTempFile(true);
            transferBufferToMediaPlayer();
        }
    }

    private void startMediaPlayer() {
        try {
            // deleteTempFile(true);
            BUFFTempFile = File.createTempFile(TEMP_BUFF_FILE_NAME,FILE_POSTFIX);
            // FileSystemUtil.copyFile(DLTempFile, BUFFTempFile);
            mPlayer = new MediaPlayer();
            setListener();
            mPlayer.setDataSource(BUFFTempFile.getAbsolutePath());
            mPlayer.prepare();
            mPlayer.start();
            wasPlayed = true;
        } catch (IOException e) {

        }
    }

    private void transferBufferToMediaPlayer() {
        try {
            boolean wasPlaying = mPlayer.isPlaying();
            int curPosition = mPlayer.getCurrentPosition();
            mPlayer.pause();
            BUFFTempFile = File.createTempFile(TEMP_BUFF_FILE_NAME,FILE_POSTFIX);
            // FileSystemUtil.copyFile(DLTempFile, BUFFTempFile);
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(BUFFTempFile.getAbsolutePath());
            mPlayer.prepare();
            mPlayer.seekTo(curPosition);
            boolean atEndOfFile = mPlayer.getDuration()- mPlayer.getCurrentPosition() <= 1000;
            if (wasPlaying || atEndOfFile) {
                mPlayer.start();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (mHandler != null) {
            // mHandler.sendEmptyMessage(Preferences.EMDIA_BUFF_CHANGE);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mHandler != null) {
            // mHandler.sendEmptyMessage(Preferences.MEDIA_ENDED);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
        if (mHandler != null) {
            // mHandler.sendEmptyMessage(Preferences.MEDIA_ERROR);
        }
        return true;
    }

    private class PlayThread extends Thread{

        private String url;

        PlayThread(String url){
            this.url=url;
        }

        @Override
        public void run() {
            if(!URLUtil.isNetworkUrl(url)){
                mPlayer=new MediaPlayer();
                setListener();
                try {
                    mPlayer.setDataSource(url);
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                playFromNet(url, 0, UNKNOWN_LENGTH);
            }
        }
    }
}
