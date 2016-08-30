package com.llc.android_coolview.wade.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.llc.android_coolview.R;
import com.llc.android_coolview.wade.view.recorderview.MovieRecorderView;


/**
 * Created by liulongchao on 2015/12/8.
 */
public class WadeRecorderActivity extends AppCompatActivity {

    private MovieRecorderView movieRecorderView;
    private Button mButton;
    private boolean isFinish;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            finishActivity();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_wade_recorder);
        initViews();
        initEvents();
    }

    private void initViews(){
        movieRecorderView= (MovieRecorderView) this.findViewById(R.id.movieRecorderView);
        mButton= (Button) this.findViewById(R.id.shoot_button);
    }

    private void initEvents(){
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    movieRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {
                        @Override
                        public void onRecordFinish() {
                            handler.sendEmptyMessage(1);
                        }
                    });
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    if(movieRecorderView.getTimeCount()>1){
                        handler.sendEmptyMessage(1);
                    }else{
                        if(movieRecorderView.getmRecordFile()!=null){
                            movieRecorderView.getmRecordFile().delete();
                        }
                        movieRecorderView.stop();
                        Toast.makeText(WadeRecorderActivity.this,"视频录制时间太短",Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isFinish=false;
        movieRecorderView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFinish=true;
    }

    private void finishActivity(){
        if(isFinish){
            movieRecorderView.stop();
            // 返回到播放页面
            Intent intent = new Intent();
            intent.putExtra("path", movieRecorderView.getmRecordFile().getAbsolutePath());
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
