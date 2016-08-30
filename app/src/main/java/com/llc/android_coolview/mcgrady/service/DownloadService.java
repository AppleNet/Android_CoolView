package com.llc.android_coolview.mcgrady.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import com.llc.android_coolview.R;
import com.llc.android_coolview.mcgrady.view.activity.McGradySingleImageActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DownloadService extends Service {

	private MyBinder binder=new MyBinder();
	private File bitmap;
	private int fileSize;//文件大小
	private int readSize;//读取的文件大小
	private NotificationManager manager;
	private String fileUrl;
	private Notification notification;
	private Intent intentActivity;
	private RemoteViews rv;
	private String imgUrl;
	private String imgName;

	private Handler handler=new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			rv.setTextViewText(R.id.tv_rv, "正在下载...");
			rv.setTextViewText(R.id.total_length, 100+"");
			if(msg.what==1){
				readSize=msg.arg1;
				rv.setProgressBar(R.id.pb_rv, fileSize, readSize, false);
				String result="";//接受百分比的值  
				double x_double=readSize*1.0;
				double tempresult=x_double/fileSize;
				DecimalFormat df1 = new DecimalFormat("0.00%");//##.00%   百分比格式，后面不足2位的用0补齐  
				result= df1.format(tempresult);
				rv.setTextViewText(R.id.read_length, result);
				notification.contentView=rv;
				if(readSize==fileSize){
					//下载完毕
					rv.setTextViewText(R.id.tv_rv, "下载完成...");
					manager.notify(0 ,notification);
					// 关闭通知
					manager.cancel(0);
					stopForeground(true);
					Toast.makeText(DownloadService.this, "下载完成", Toast.LENGTH_LONG).show();
					Intent intent=new Intent(Intent.ACTION_VIEW);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					intent.setDataAndType(Uri.fromFile(binder.getImageFile()), "image/*");
					startActivity(intent);
				}else{
					manager.notify(0 ,notification);
					startForeground(0, notification);
				}
			}
			return false;
		}
	});

	@Override
	public IBinder onBind(Intent intent) {

		imgUrl=intent.getStringExtra("url");
		new Thread(){

			@Override
			public void run() {
				super.run();
				InputStream is=null;
				FileOutputStream fos=null;
				int readSize=0;
				try {
					URL url=new URL(imgUrl);
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("User-Agent", "NetFox");
					conn.setRequestProperty("RANGE", "bytes="+ readSize +"-");
					fileSize=conn.getContentLength();
					if(fileUrl==null){
						imgName=String.valueOf(System.currentTimeMillis());
						fileUrl=Environment.getExternalStorageDirectory().getAbsolutePath()+"/ImageCache/"+imgName+".jpg";
					}
					File cacheFile=new File(fileUrl);
					if(!cacheFile.exists()){
						cacheFile.getParentFile().mkdirs();
						cacheFile.createNewFile();
					}
					fos=new FileOutputStream(cacheFile, true);
					is=conn.getInputStream();
					byte[] buffer=new byte[4096];
					int length=-1;
					while((length=is.read(buffer))!=-1){
						fos.write(buffer, 0, length);
						readSize+=length;
						Message message=new Message();
						message.what=1;
						message.arg1=readSize;
						handler.sendMessage(message);
						try{
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


		}.start();
		return binder;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		String tickerText="开始下载...";
		long when = System.currentTimeMillis();
		notification=new Notification(R.drawable.logo_pinterest, tickerText, when);
		intentActivity=new Intent(DownloadService.this,McGradySingleImageActivity.class);
		PendingIntent pi=PendingIntent.getActivity(DownloadService.this, 0, intentActivity, 0);
		notification.contentIntent=pi;
		rv=new RemoteViews(getPackageName(), R.layout.activity_mcgrady_notification_layout);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopForeground(true);
	}

	public class MyBinder extends Binder{
		public File getImageFile(){
			bitmap=new File(fileUrl);
			return bitmap;
		}

		public String getImgName(){
			return imgName;
		}
	}

}
