package com.llc.android_coolview.kobe.view.dialog;

import java.util.HashMap;

import com.llc.android_coolview.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

public class TrainDetailDialog extends Dialog implements android.view.View.OnClickListener {

	private TextView ok;
	private View highSpeedRail,motorCar,nonStop,expressTrain,quickTrain,other;
	private CheckBox hsrCk,mcCk,nsCk,etCk,qtCk,oCk;
	private TextView hsrTv,mcTv,nsTv,etTv,qtTv,oTv;

	private HashMap<Integer, String> items=new HashMap<>();
	private String text;
	private String [] trains;
	public TrainDetailDialog(Context context,String text) {
		super(context);
		this.text=text;
	}

	public TrainDetailDialog(Context context, int theme) {
		super(context, theme);
	}

	public TrainDetailDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_kobe_fragment_tickets_train_detail_dialog);
		initView();
		initDatas();
		setOnClickListener();
	}

	private void initView(){
		ok=(TextView) this.findViewById(R.id.ok_tv);
		highSpeedRail=this.findViewById(R.id.high_speed_rail);
		motorCar=this.findViewById(R.id.motor_car);
		nonStop=this.findViewById(R.id.non_stop);
		expressTrain=this.findViewById(R.id.express_train);
		quickTrain=this.findViewById(R.id.quick_train);
		other=this.findViewById(R.id.other);
		hsrCk=(CheckBox) this.findViewById(R.id.high_speed_rail_ck);
		mcCk=(CheckBox) this.findViewById(R.id.motor_car_ck);
		nsCk=(CheckBox) this.findViewById(R.id.non_stop_ck);
		etCk=(CheckBox) this.findViewById(R.id.express_train_ck);
		qtCk=(CheckBox) this.findViewById(R.id.quick_train_ck);
		oCk=(CheckBox) this.findViewById(R.id.other_ck);
		hsrTv=(TextView) this.findViewById(R.id.high_speed_rail_tv);
		mcTv=(TextView) this.findViewById(R.id.motor_car_tv);
		nsTv=(TextView) this.findViewById(R.id.non_stop_tv);
		etTv=(TextView) this.findViewById(R.id.express_train_tv);
		qtTv=(TextView) this.findViewById(R.id.quick_train_tv);
		oTv=(TextView) this.findViewById(R.id.other_tv);
	}

	private void initDatas(){
		trains=text.split(",");
		items.clear();
		for(int i=0;i<trains.length;i++){
			switch (trains[i]) {
				case "高铁":
					hsrCk.setChecked(true);
					items.put(R.id.high_speed_rail, hsrTv.getText().toString().trim());
					break;
				case "动车/城际":
					mcCk.setChecked(true);
					items.put(R.id.motor_car, mcTv.getText().toString().trim());
					break;
				case "Z直达":
					nsCk.setChecked(true);
					items.put(R.id.non_stop, nsTv.getText().toString().trim());
					break;
				case "T特快":
					etCk.setChecked(true);
					items.put(R.id.express_train, etTv.getText().toString().trim());
					break;
				case "K快速":
					qtCk.setChecked(true);
					items.put(R.id.quick_train, qtTv.getText().toString().trim());
					break;
				case "其他":
					oCk.setChecked(true);
					items.put(R.id.other, oTv.getText().toString().trim());
					break;
				default:
					break;
			}
		}
	}

	private void setOnClickListener(){
		ok.setOnClickListener(this);
		highSpeedRail.setOnClickListener(this);
		motorCar.setOnClickListener(this);
		nonStop.setOnClickListener(this);
		expressTrain.setOnClickListener(this);
		quickTrain.setOnClickListener(this);
		other.setOnClickListener(this);
	}

	public interface OnCheckedChangedListener{
		public void checkChanged(HashMap<Integer, String> items);
	}

	private OnCheckedChangedListener mOnCheckedChangedListener;

	public void setmOnCheckedChangedListener(OnCheckedChangedListener mOnCheckedChangedListener) {
		this.mOnCheckedChangedListener = mOnCheckedChangedListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ok_tv:
				if(hsrCk.isChecked()&&mcCk.isChecked()&&nsCk.isChecked()&&etCk.isChecked()&&qtCk.isChecked()&&oCk.isChecked()){
					items.clear();
					items.put(0, "全部");
				}
				mOnCheckedChangedListener.checkChanged(items);
				break;
			case R.id.high_speed_rail:
				if(hsrCk.isChecked()){
					items.remove(R.id.high_speed_rail);
					hsrCk.setChecked(false);
				}else{
					if(!items.containsKey(R.id.high_speed_rail)){
						items.put(R.id.high_speed_rail, hsrTv.getText().toString().trim());
					}
					hsrCk.setChecked(true);
				}
				break;
			case R.id.motor_car:
				if(mcCk.isChecked()){
					items.remove(R.id.motor_car);
					mcCk.setChecked(false);
				}else{
					if(!items.containsKey(R.id.motor_car)){
						items.put(R.id.motor_car, mcTv.getText().toString().trim());
					}
					mcCk.setChecked(true);
				}
				break;
			case R.id.non_stop:
				if(nsCk.isChecked()){
					items.remove(R.id.non_stop);
					nsCk.setChecked(false);
				}else {
					if(!items.containsKey(R.id.non_stop)){
						items.put(R.id.non_stop, nsTv.getText().toString().trim());
					}
					nsCk.setChecked(true);
				}
				break;
			case R.id.express_train:
				if(etCk.isChecked()){
					items.remove(R.id.express_train);
					etCk.setChecked(false);
				}else {
					if(!items.containsKey(R.id.express_train)){
						items.put(R.id.express_train, etTv.getText().toString().trim());
					}
					etCk.setChecked(true);
				}
				break;
			case R.id.quick_train:
				if(qtCk.isChecked()){
					items.remove(R.id.quick_train);
					qtCk.setChecked(false);
				}else {
					if(!items.containsKey(R.id.quick_train)){
						items.put(R.id.quick_train, qtTv.getText().toString().trim());
					}
					qtCk.setChecked(true);
				}
				break;
			case R.id.other:
				if(oCk.isChecked()){
					items.remove(R.id.other);
					oCk.setChecked(false);
				}else {
					if(!items.containsKey(R.id.other)){
						items.put(R.id.other, oTv.getText().toString().trim());
					}
					oCk.setChecked(true);
				}
				break;
			default:
				items.clear();
				break;
		}
	}
}
