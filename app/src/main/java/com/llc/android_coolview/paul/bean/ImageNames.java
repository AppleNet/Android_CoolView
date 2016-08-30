package com.llc.android_coolview.paul.bean;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;

import com.llc.android_coolview.R;
import com.llc.android_coolview.util.BitmapUtils;

public class ImageNames {

	public static final int[] images=new int[]{
			R.drawable.image_76ers,
			R.drawable.image_blazers,
			R.drawable.image_bucks,
			R.drawable.image_bulls,
			R.drawable.image_cavs,
			R.drawable.image_celtics,
			R.drawable.image_clippers,
			R.drawable.image_grizzlies,R.drawable.image_hawks,R.drawable.image_heat,R.drawable.image_hornets,
			R.drawable.image_jazz,R.drawable.image_kings,R.drawable.image_knicks,R.drawable.image_lakers,
			R.drawable.image_magic,R.drawable.image_mavericks,R.drawable.image_nets,R.drawable.image_nuggets,
			R.drawable.image_orleans,R.drawable.image_pacers,R.drawable.image_pistons,R.drawable.image_raptors,
			R.drawable.image_rockets,R.drawable.image_spurs,R.drawable.image_suns,R.drawable.image_thunder,
			R.drawable.image_warriors,R.drawable.image_wizars,R.drawable.image_wolfs
	};

	public static final String[] teamName=new String[]{
			"76人",
			"开拓者",
			"雄鹿",
			"公牛",
			"骑士",
			"凯尔特人",
			"快船",
			"灰熊","老鹰","热火","黄蜂","爵士","国王","尼克斯","湖人","魔术","小牛","篮网",
			"掘金","鹈鹕","步行者","活塞","猛龙","火箭","马刺","太阳","雷霆","勇士","奇才","森林狼"
	};


	public static List<Bitmap> getBitmap(Context mContext){

		List<Bitmap> imgBitmaps=new ArrayList<Bitmap>();
		for(int i=0;i<images.length;i++){
			Bitmap bitmap=BitmapUtils.readBitMap(mContext, images[i]);
			if(bitmap!=null){
				imgBitmaps.add(bitmap);
			}
		}
		return imgBitmaps;
	}



	public static List<String> getTeamName(){
		List<String> teamNames=new ArrayList<String>();
		for(int i=0;i<teamName.length;i++){
			teamNames.add(teamName[i]);
		}
		return teamNames;
	}

}
