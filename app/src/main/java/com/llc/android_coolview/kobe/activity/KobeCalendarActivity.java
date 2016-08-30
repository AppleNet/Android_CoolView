package com.llc.android_coolview.kobe.activity;

import java.util.Calendar;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.Constant;
import com.llc.android_coolview.kobe.calendar.DatepickerParam;
import com.llc.android_coolview.kobe.view.seekbar.SeekBarPressure;
import com.llc.android_coolview.kobe.view.seekbar.SeekBarPressure.OnSeekBarChangeListener;
import com.llc.android_coolview.util.DateUtils;
import com.llc.android_coolview.util.ScreenUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class KobeCalendarActivity extends Activity implements OnClickListener {

	private ScrollView mScrollView;
	private SeekBarPressure mSeekBarPressure;
	private DatepickerParam mDatepickerParam;
	private Context mContext=this;
	private int scrollHeight=0;
	private LinearLayout mLinearLayoutSelected;

	private int pressed=android.R.attr.state_pressed;
	private int enabled=android.R.attr.state_enabled;
	private int selected=android.R.attr.state_selected;
	private Intent intent;
	private int weekOfDay;
	private String date;

	private TextView timeLow,timeHigh;

	private Handler handler=new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what==11){
				mScrollView.scrollTo(0, scrollHeight);
				mScrollView.setVisibility(View.VISIBLE);
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kobe_fragment_tickets_calendar);
		intent=getIntent();
		if(intent!=null){
			date=intent.getStringExtra("date");
		}
		timeLow=(TextView) this.findViewById(R.id.time_low);
		timeHigh=(TextView) this.findViewById(R.id.time_high);
		mScrollView=(ScrollView) this.findViewById(R.id.myscrollview);
		mScrollView.setVisibility(View.INVISIBLE);
		mSeekBarPressure=(SeekBarPressure) this.findViewById(R.id.seekBar_tg2);
		mSeekBarPressure.setOnSeekBarChangeListener(onSeekBarChangeListener);
		mDatepickerParam=new DatepickerParam();
		mDatepickerParam.startDate=DateUtils.getCurrentDateTime();
		mDatepickerParam.dateRange=60;
		mDatepickerParam.selectedDay=DateUtils.getCalendar(date);
		mLinearLayoutSelected=(LinearLayout) View.inflate(this, R.layout.activity_kobe_fragment_tickets_date_pick_header, null);
		LinearLayout localLinearLayout1=new LinearLayout(this);
		localLinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		localLinearLayout1.setOrientation(LinearLayout.VERTICAL);
		mScrollView.addView(localLinearLayout1);
		localLinearLayout1.setPadding(ScreenUtil.dip2px(mContext, 5f), ScreenUtil.dip2px(mContext, 5f), ScreenUtil.dip2px(mContext, 5f), 0);
		Calendar localCalendar1=(Calendar) mDatepickerParam.startDate.clone();
		Calendar calendarToday=(Calendar) localCalendar1.clone();
		Calendar calendarTommorrwo=(Calendar) localCalendar1.clone();
		calendarTommorrwo.add(Calendar.DAY_OF_MONTH, 1);
		Calendar calendarTwoAfterDay=(Calendar) localCalendar1.clone();
		calendarTwoAfterDay.add(Calendar.DAY_OF_MONTH, 2);
		Calendar calendarSelected=(Calendar) mDatepickerParam.selectedDay.clone();
		int yearOfLocalCalendar1=localCalendar1.get(Calendar.YEAR);
		int monthOfLocalCalendar1=localCalendar1.get(Calendar.MONTH);
		Calendar localCalendarEnd=(Calendar) mDatepickerParam.startDate.clone();
		localCalendarEnd.add(Calendar.DAY_OF_MONTH, mDatepickerParam.dateRange-1);
		int yearOfLocalCalendar2=localCalendarEnd.get(Calendar.YEAR);
		int monthOfLocalCalendar2=localCalendarEnd.get(Calendar.MONTH);

		int differYear=yearOfLocalCalendar2-yearOfLocalCalendar1;
		int differMonth=monthOfLocalCalendar2-monthOfLocalCalendar1;
		int totalDiffer=differYear * 12+differMonth+1;
		for(int i=0;i<totalDiffer;i++){
			LinearLayout localLinearLayout2=(LinearLayout) View.inflate(this, R.layout.activity_kobe_fragment_tickets_date_pick_header, null);
			localLinearLayout1.addView(localLinearLayout2,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			TextView year=(TextView) localLinearLayout2.findViewById(R.id.tv_cal_year);
			TextView month=(TextView) localLinearLayout2.findViewById(R.id.tv_cal_month);
			Calendar yearCalendar=(Calendar) localCalendar1.clone();
			yearCalendar.add(Calendar.YEAR, i / 11);
			year.setText(yearCalendar.get(Calendar.YEAR)+getResources().getString(R.string.year));
			Calendar monthCalendar=(Calendar) localCalendar1.clone();
			monthCalendar.add(Calendar.MONTH, i);
			month.setText(monthCalendar.get(Calendar.MONTH)+1+getResources().getString(R.string.month));

			monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
			weekOfDay=monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
			int maxMonth=monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int lines=(int) Math.ceil((weekOfDay+maxMonth)/7.0f);

			for (int j = 0; j < lines; j++) {
				LinearLayout oneLineLinearLayout=getOneLineDayLinearLayout();
				if(j==0){
					for (int k = 0; k < 7; k++) {
						TextView localTextView=(TextView) (((RelativeLayout) oneLineLinearLayout.getChildAt(k)).getChildAt(0));
						RelativeLayout localSelectedRela = (RelativeLayout) (((RelativeLayout) oneLineLinearLayout.getChildAt(k)).getChildAt(1));
						TextView localTextViewSelected=(TextView) localSelectedRela.getChildAt(0);
						if(k>=weekOfDay){
							int index=k-weekOfDay+1;
							localTextView.setText(index + "");
							localTextViewSelected.setText(index+"");
							Calendar tempCalendar3=(Calendar) monthCalendar.clone();
							tempCalendar3.set(Calendar.DAY_OF_MONTH, index);
							String date=tempCalendar3.get(Calendar.YEAR)+ "-"+ (tempCalendar3.get(Calendar.MONTH) + 1)+ "-"+ tempCalendar3.get(Calendar.DAY_OF_MONTH);
							localTextView.setTag(Long.valueOf(tempCalendar3.getTimeInMillis()));
							localSelectedRela.setTag(Long.valueOf(tempCalendar3.getTimeInMillis()));
							if (compareCal(tempCalendar3, calendarToday) == -1) {
								localTextView.setTextColor(getResources().getColor(R.color.calendar_color_gray));
								localTextView.setEnabled(false);
							}
							if (Constant.HOLIDAYS.get(date) != null) {
								localTextView.setText(Constant.HOLIDAYS.get(date));
								localTextViewSelected.setText(Constant.HOLIDAYS.get(date));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
								localTextViewSelected.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
								localTextView.setTextColor(getTextColorGreen());
							}
							if (compareCal(tempCalendar3, calendarToday) == 0) {
								localTextView.setTextColor(getTextColorRed());
								localTextView.setText(getResources().getString(R.string.today));
								localTextViewSelected.setText(getResources().getString(R.string.today));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
							}
							if (compareCal(tempCalendar3, calendarTommorrwo) == 0) {
								localTextView.setTextColor(getTextColorRed());
								localTextView.setText(getResources().getString(R.string.tommorrow));
								localTextViewSelected.setText(getResources().getString(R.string.tommorrow));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
							}
							if (compareCal(tempCalendar3, calendarTwoAfterDay) == 0) {
								localTextView.setTextColor(getTextColorRed());
								localTextView.setText(getResources().getString(R.string.aftertoday));
								localTextViewSelected.setText(getResources().getString(R.string.aftertoday));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
							}

							if (compareCal(tempCalendar3, calendarSelected) == 0) {
								localTextView.setVisibility(View.INVISIBLE);
								localSelectedRela.setVisibility(View.VISIBLE);
								localSelectedRela.setSelected(true);
								mLinearLayoutSelected = localLinearLayout2;
							}

							if (compareCal(tempCalendar3, localCalendarEnd) == 1) {
								localTextView.setTextColor(getResources().getColor(R.color.calendar_color_gray));
								localTextView.setEnabled(false);
							}
						}else{
							localTextView.setVisibility(View.INVISIBLE);
						}
					}
				}else if(j==lines-1){
					int temp = maxMonth - (lines - 2) * 7 - (7 - weekOfDay);
					for(int k=0;k<7;k++){
						TextView localTextView = (TextView) (((RelativeLayout) oneLineLinearLayout.getChildAt(k)).getChildAt(0));
						RelativeLayout localSelectedRela = (RelativeLayout) (((RelativeLayout) oneLineLinearLayout.getChildAt(k)).getChildAt(1));
						TextView localTextViewSelected = (TextView) localSelectedRela.getChildAt(0);
						if(k<temp){
							int index = (7 - weekOfDay) + (j - 1) * 7 + k + 1;
							localTextView.setText(index + "");
							localTextViewSelected.setText(index + "");
							Calendar tempCalendar3 = (Calendar) monthCalendar.clone();
							tempCalendar3.set(Calendar.DAY_OF_MONTH, index);
							String date = tempCalendar3.get(Calendar.YEAR)+ "-"+ (tempCalendar3.get(Calendar.MONTH) + 1)+ "-"+ tempCalendar3.get(Calendar.DAY_OF_MONTH);
							localTextView.setTag(Long.valueOf(tempCalendar3.getTimeInMillis()));
							localSelectedRela.setTag(Long.valueOf(tempCalendar3.getTimeInMillis()));
							if (compareCal(tempCalendar3, calendarToday) == -1) {
								localTextView.setTextColor(getResources().getColor(R.color.calendar_color_gray));
								localTextView.setEnabled(false);
							}
							if (Constant.HOLIDAYS.get(date) != null) {
								localTextView.setText(Constant.HOLIDAYS.get(date));
								localTextViewSelected.setText(Constant.HOLIDAYS.get(date));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
								localTextViewSelected.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
								localTextView.setTextColor(getTextColorGreen());
							}

							if (compareCal(tempCalendar3, calendarToday) == 0) {
								localTextView.setTextColor(getTextColorRed());
								localTextView.setText(getResources().getString(R.string.today));
								localTextViewSelected.setText(getResources().getString(R.string.today));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
							}
							if (compareCal(tempCalendar3, calendarTommorrwo) == 0) {
								localTextView.setTextColor(getTextColorRed());
								localTextView.setText(getResources().getString(R.string.tommorrow));
								localTextViewSelected.setText(getResources().getString(R.string.tommorrow));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
							}
							if (compareCal(tempCalendar3, calendarTwoAfterDay) == 0) {
								localTextView.setTextColor(getTextColorRed());
								localTextView.setText(getResources().getString(R.string.aftertoday));
								localTextViewSelected.setText(getResources().getString(R.string.aftertoday));
								localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
							}

							if (compareCal(tempCalendar3, calendarSelected) == 0) {
								localTextView.setVisibility(View.INVISIBLE);
								localSelectedRela.setVisibility(View.VISIBLE);
								localSelectedRela.setSelected(true);
								mLinearLayoutSelected = localLinearLayout2;
							}

							if (compareCal(tempCalendar3, localCalendarEnd) == 1) {
								localTextView.setTextColor(getResources()
										.getColor(R.color.calendar_color_gray));
								localTextView.setEnabled(false);
							}
						}else{
							localTextView.setVisibility(View.INVISIBLE);
						}
					}
				}else{
					for (int k = 0; k < 7; k++) {
						TextView localTextView = (TextView) (((RelativeLayout) oneLineLinearLayout.getChildAt(k)).getChildAt(0));
						RelativeLayout localSelectedRela = (RelativeLayout) (((RelativeLayout) oneLineLinearLayout.getChildAt(k)).getChildAt(1));
						TextView localTextViewSelected = (TextView) localSelectedRela.getChildAt(0);
						int index = (7 - weekOfDay) + (j - 1) * 7 + k + 1;
						localTextView.setText(index + "");
						localTextViewSelected.setText(index + "");
						Calendar tempCalendar3 = (Calendar) monthCalendar.clone();
						tempCalendar3.set(Calendar.DAY_OF_MONTH, index);
						String date = tempCalendar3.get(Calendar.YEAR) + "-"+ (tempCalendar3.get(Calendar.MONTH) + 1) + "-"+ tempCalendar3.get(Calendar.DAY_OF_MONTH);
						localTextView.setTag(Long.valueOf(tempCalendar3.getTimeInMillis()));
						localSelectedRela.setTag(Long.valueOf(tempCalendar3.getTimeInMillis()));
						if (compareCal(tempCalendar3, calendarToday) == -1) {
							localTextView.setTextColor(getResources().getColor(R.color.calendar_color_gray));
							localTextView.setEnabled(false);
						}
						if (Constant.HOLIDAYS.get(date) != null) {
							localTextView.setText(Constant.HOLIDAYS.get(date));
							localTextViewSelected.setText(Constant.HOLIDAYS.get(date));
							localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
							localTextViewSelected.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
							localTextView.setTextColor(getTextColorGreen());
						}

						if (compareCal(tempCalendar3, calendarToday) == 0) {
							localTextView.setTextColor(getTextColorRed());
							localTextView.setText(getResources().getString(R.string.today));
							localTextViewSelected.setText(getResources().getString(R.string.today));
							localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
						}
						if (compareCal(tempCalendar3, calendarTommorrwo) == 0) {
							localTextView.setTextColor(getTextColorRed());
							localTextView.setText(getResources().getString(R.string.tommorrow));
							localTextViewSelected.setText(getResources().getString(R.string.tommorrow));
							localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
						}
						if (compareCal(tempCalendar3, calendarTwoAfterDay) == 0) {
							localTextView.setTextColor(getTextColorRed());
							localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
							localTextView.setText(getResources().getString(R.string.aftertoday));
							localTextViewSelected.setText(getResources().getString(R.string.aftertoday));
						}

						if (compareCal(tempCalendar3, calendarSelected) == 0) {
							localTextView.setVisibility(View.INVISIBLE);
							localSelectedRela.setVisibility(View.VISIBLE);
							localSelectedRela.setSelected(true);
							mLinearLayoutSelected = localLinearLayout2;
						}
						if (compareCal(tempCalendar3, localCalendarEnd) == 1) {
							localTextView.setTextColor(getResources().getColor(R.color.calendar_color_gray));
							localTextView.setEnabled(false);
						}

					}
				}
				localLinearLayout1.addView(oneLineLinearLayout);
			}
		}
	}

	/**
	 * 获取一行 七天的LinearLayout
	 *
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private LinearLayout getOneLineDayLinearLayout(){
		LinearLayout localLinearLayout=new LinearLayout(this);
		localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		localLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		for(int i=0;i<7;i++){
			float height=(ScreenUtil.getScreenWidth(this)-ScreenUtil.dip2px(this, 10f)-ScreenUtil.dip2px(this, 1.5f * 6)) / 7;
			LinearLayout.LayoutParams localLayoutParams4=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)height,1.0f);
			RelativeLayout localRelativeLayout=new RelativeLayout(this);
			localRelativeLayout.setLayoutParams(localLayoutParams4);
			localLayoutParams4.setMargins(ScreenUtil.dip2px(this, 1.5F),ScreenUtil.dip2px(this, 1.5F),ScreenUtil.dip2px(this, 1.5F),ScreenUtil.dip2px(this, 1.5F));
			TextView localTextView3 = new TextView(this);
			localTextView3.setLayoutParams(localLayoutParams4);
			localTextView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0F);
			localTextView3.setBackgroundDrawable(getBackGroundDrawable());
			localTextView3.setTextColor(getTextColorBlack());
			localTextView3.setGravity(Gravity.CENTER);
			localTextView3.setOnClickListener(this);
			localTextView3.setVisibility(View.VISIBLE);
			localRelativeLayout.addView(localTextView3);

			RelativeLayout localRelativeLayout2=new RelativeLayout(this);
			localRelativeLayout2.setLayoutParams(localLayoutParams4);
			localRelativeLayout2.setOnClickListener(this);
			localRelativeLayout2.setBackgroundDrawable(getBackGroundDrawable());

			TextView localTextView1=new TextView(this);
			localTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0F);
			localTextView1.setId(1);
			localTextView1.setTextColor(getTextColorBlack());

			RelativeLayout.LayoutParams localLayoutParams2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			localLayoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
			localLayoutParams2.topMargin= ScreenUtil.dip2px(this, 4f);
			localRelativeLayout2.addView(localTextView1, 0,localLayoutParams2);

			TextView localTextView2=new TextView(this);
			localTextView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0F);
			localTextView2.setTextColor(this.getResources().getColor(R.color.calendar_color_white));
			localTextView2.setText(getResources().getString(R.string.go));

			RelativeLayout.LayoutParams localLayoutParams3=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			localLayoutParams3.addRule(RelativeLayout.CENTER_HORIZONTAL);
			localLayoutParams3.addRule(RelativeLayout.BELOW, 1);
			localRelativeLayout2.addView(localTextView2, 1, localLayoutParams3);

			localRelativeLayout2.setVisibility(View.INVISIBLE);
			localRelativeLayout.addView(localRelativeLayout2,1);

			localLinearLayout.addView(localRelativeLayout,i);
		}
		return localLinearLayout;
	}

	/**
	 * 点击背景切换
	 *
	 * @return
	 */
	private StateListDrawable getBackGroundDrawable() {
		int pressed = android.R.attr.state_pressed;
		int enabled = android.R.attr.state_enabled;
		int selected = android.R.attr.state_selected;

		StateListDrawable localStateListDrawable = new StateListDrawable();
		ColorDrawable localColorDrawable1 = new ColorDrawable(this.getResources().getColor(android.R.color.transparent));
		Drawable localColorDrawable2 = this.getResources().getDrawable(R.drawable.bg_calendar_seleced);
		ColorDrawable localColorDrawable3 = new ColorDrawable(this.getResources().getColor(android.R.color.transparent));
		localStateListDrawable.addState(new int[] { pressed, enabled },localColorDrawable2);
		localStateListDrawable.addState(new int[] { selected, enabled },localColorDrawable2);
		localStateListDrawable.addState(new int[] { enabled },localColorDrawable1);
		localStateListDrawable.addState(new int[0], localColorDrawable3);
		return localStateListDrawable;
	}

	/**
	 * 字体颜色 切换
	 *
	 * @return
	 */
	private ColorStateList getTextColorBlack(){
		return new ColorStateList(new int[][] { { pressed, enabled },{ selected, enabled }, { enabled }, new int[0] }, new int[] {-1, -1,this.getResources().getColor(R.color.calendar_color_black),this.getResources().getColor(R.color.calendar_color_white) });
	}

	private ColorStateList getTextColorRed(){
		return new ColorStateList(new int[][] {{ pressed, enabled },{ selected, enabled }, { enabled }, new int[0] }, new int[] {-1, -1,this.getResources().getColor(R.color.calendar_color_orange),this.getResources().getColor(R.color.calendar_color_white) });
	}

	private ColorStateList getTextColorGreen(){
		return new ColorStateList(new int[][] { { pressed, enabled },{ selected, enabled }, { enabled }, new int[0] }, new int[] {-1, -1,this.getResources().getColor(R.color.calendar_color_green),this.getResources().getColor(R.color.calendar_color_white) });
	}

	/**
	 * 比较两个日期的大小
	 *
	 * @param paramCalendar1
	 * @param paramCalendar2
	 * @return
	 */
	private int compareCal(Calendar paramCalendar1, Calendar paramCalendar2) {
		if (paramCalendar1.get(Calendar.YEAR) > paramCalendar2.get(Calendar.YEAR)) {
			return 1;
		} else if (paramCalendar1.get(Calendar.YEAR) < paramCalendar2.get(Calendar.YEAR)) {
			return -1;
		} else {
			if (paramCalendar1.get(Calendar.MONTH) > paramCalendar2.get(Calendar.MONTH)) {
				return 1;
			} else if (paramCalendar1.get(Calendar.MONTH) < paramCalendar2.get(Calendar.MONTH)) {
				return -1;
			} else {
				if (paramCalendar1.get(Calendar.DAY_OF_MONTH) > paramCalendar2.get(Calendar.DAY_OF_MONTH)) {
					return 1;
				} else if (paramCalendar1.get(Calendar.DAY_OF_MONTH) < paramCalendar2.get(Calendar.DAY_OF_MONTH)) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v!=null){
			Calendar localCalendar = Calendar.getInstance();
			localCalendar.setTimeInMillis(((Long) v.getTag()).longValue());
			String dateResult=(localCalendar.get(Calendar.MONTH) + 1) + getResources().getString(R.string.month)+ localCalendar.get(Calendar.DAY_OF_MONTH) + getResources().getString(R.string.day);
			String weekResult=DateUtils.computeWeekC(weekOfDay);
			intent.putExtra("DATE", dateResult);
			intent.putExtra("WEEK", weekResult);
			setResult(1, intent);
			Toast.makeText(this,localCalendar.get(Calendar.YEAR) + getResources().getString(R.string.year)+ (localCalendar.get(Calendar.MONTH) + 1) + getResources().getString(R.string.month)+ localCalendar.get(Calendar.DAY_OF_MONTH) + getResources().getString(R.string.day),Toast.LENGTH_SHORT).show();
			this.finish();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		scrollHeight = mLinearLayoutSelected.getTop();
		handler.sendEmptyMessageDelayed(11, 100l);
	}

	OnSeekBarChangeListener onSeekBarChangeListener=new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBarPressure seekBar, double progressLow,double progressHigh, int mprogressLow, int mprogressHigh,double max, double min) {
			timeLow.setText(progressLow+"");
			timeHigh.setText(progressHigh+"");
		}
	};
}
