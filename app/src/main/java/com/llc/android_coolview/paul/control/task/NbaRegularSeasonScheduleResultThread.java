package com.llc.android_coolview.paul.control.task;

import com.llc.android_coolview.paul.control.PaulManager;
import com.llc.android_coolview.paul.view.activity.NBAGamesSpreadActivity;

import android.os.Handler;
import android.os.Message;

public class NbaRegularSeasonScheduleResultThread extends Thread {

	private Handler handler;
	
	public NbaRegularSeasonScheduleResultThread(Handler handler){
		this.handler=handler;
	}
	
	@Override
	public void run() {
		super.run();
		String result=PaulManager.getNbaRegularSeasonScheduleResult();
		Message msg=new Message();
		msg.what=NBAGamesSpreadActivity.THE_NBA_REGULAR_SEASON_SCHEDULE_RESULT;
		msg.obj=result;
		handler.sendMessage(msg);
	}

	
}
