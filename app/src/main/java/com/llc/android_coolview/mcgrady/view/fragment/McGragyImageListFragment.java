package com.llc.android_coolview.mcgrady.view.fragment;

import com.llc.android_coolview.R;
import com.llc.android_coolview.animation.SwitchAnimationUtil;
import com.llc.android_coolview.mcgrady.controll.McgRadyListImgItemAdapter;
import com.llc.android_coolview.mcgrady.model.Images;
import com.llc.android_coolview.mcgrady.view.activity.McGradyActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class McGragyImageListFragment extends Fragment {

	private GridView gridView;
	private String[] mUrlStrs = Images.imageThumbUrls;
	private View view;
	private SwitchAnimationUtil switchAnimationUtil;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.activity_mcgrady_fragment_list_imgs, container,false);
		initView();
		setAdapter();
		return view;
	}
	
	private void initView(){
		gridView=(GridView) view.findViewById(R.id.id_gridview);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((McGradyActivity)getActivity()).setOnResumeWindowFocusChanged(new McGradyActivity.OnResumeWindowFocusChanged() {
			@Override
			public void resumeWindowFoucsChanged() {
				if(switchAnimationUtil==null){
					switchAnimationUtil=new SwitchAnimationUtil();
					switchAnimationUtil.startAnimation(gridView, SwitchAnimationUtil.AnimationType.ROTATE);
				}
			}
		});
	}

	private void setAdapter(){
		if (getActivity() == null || gridView == null)
			return;
		if(mUrlStrs!=null){
			gridView.setAdapter(new McgRadyListImgItemAdapter(getActivity(), 0, mUrlStrs));
		}else{
			gridView.setAdapter(null);
		}
	}
}
