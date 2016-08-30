package com.llc.android_coolview.wade.adapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.llc.android_coolview.R;
import com.llc.android_coolview.wade.adapter.holder.ViewHolder;
import com.llc.android_coolview.wade.adapter.impl.CommonAdapter;

public class MyAdapter extends CommonAdapter<String> {

	/**
	 * 文件夹路径
	 */
	private String mDirPath;
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	private ArrayList<String> mSelectImage=new ArrayList<String>();

	/**
	 * Handler，发消息 通知主线程更新UI
	 * */
	private Handler handler;

	public MyAdapter(Context context, List<String> mList, int itemLayoutId,String dirPath,Handler handler) {
		super(context, mList, itemLayoutId);
		this.mDirPath=dirPath;
		this.handler=handler;
	}

	@Override
	public void convert(ViewHolder holder, final String item) {
		// 设置no_pic
		holder.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		// 设置no_select
		holder.setImageResource(R.id.id_item_select, R.drawable.picture_unselected);
		// 设置图片
		File file=new File(mDirPath+"/"+item);
		if(file.isDirectory()){
			holder.setImageResource(R.id.id_item_image, R.drawable.image_folder);
			holder.setImageViewVisible(R.id.id_item_select, false);
		}else{
			holder.setImageByUrl(R.id.id_item_image, mDirPath+"/"+item);
		}
		final ImageView imageView=holder.getView(R.id.id_item_image);
		final ImageView imageSelect=holder.getView(R.id.id_item_select);
		imageView.setColorFilter(null);
		imageView.setOnClickListener(new OnClickListener() {
			// 选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v) {
				if(imageSelect.isShown()){
					if(mSelectImage.contains(mDirPath+"/"+item)){
						mSelectImage.remove(mDirPath+"/"+item);
						imageSelect.setImageResource(R.drawable.picture_unselected);
						imageView.setColorFilter(null);
					}else{
						mSelectImage.add(mDirPath+"/"+item);
						imageSelect.setImageResource(R.drawable.pictures_selected);
						imageView.setColorFilter(Color.parseColor("#77000000"));
					}
				}else{
					new Thread(){

						@Override
						public void run() {
							super.run();
							File file=new File(mDirPath+"/"+item);
							List<String> nextDirList=Arrays.asList(file.list(new FilenameFilter() {

								@Override
								public boolean accept(File dir, String filename) {
									if (filename.endsWith(".jpg") || filename.endsWith(".png")
											|| filename.endsWith(".jpeg"))
										return true;
									return false;
								}
							}));

							Message msg=new Message();
							Bundle bundle=new Bundle();
							bundle.putString("ImgDir", mDirPath+"/"+item);
							bundle.putInt("ImgCount", nextDirList.size());
							bundle.putString("ImgDirName", item);
							msg.setData(bundle);
							msg.obj=nextDirList;
							msg.what=1;
							handler.sendMessage(msg);
						}
					}.start();
				}

			}
		});
		if(mSelectImage.contains(mDirPath+"/"+item)){
			imageSelect.setImageResource(R.drawable.pictures_selected);
			imageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}
	public ArrayList<String> getSelectImg(){
		return mSelectImage;
	}


}
