package com.llc.android_coolview.wade.view.popupwindow;

import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.wade.adapter.holder.ViewHolder;
import com.llc.android_coolview.wade.adapter.impl.CommonAdapter;
import com.llc.android_coolview.wade.bean.ImageFolder;
import com.llc.android_coolview.wade.view.popupwindow.impl.BasePopupWindowForListView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFolder> {

	private ListView mListDir;

	public ListImageDirPopupWindow(int width, int height,List<ImageFolder> datas, View convertView) {
		super(convertView, width, height, true, datas);
	}

	@Override
	protected void init() {

	}

	@Override
	protected void beforeInitWeNeedSomeParams(Object... params) {

	}

	@Override
	protected void initViews() {
		mListDir=(ListView) findViewById(R.id.id_list_dir);
		mListDir.setAdapter(new CommonAdapter<ImageFolder>(context,mList,R.layout.activity_wade_list_dir_item) {

			@Override
			public void convert(ViewHolder holder, ImageFolder item) {
				holder.setText(R.id.id_dir_item_name, item.getName());
				holder.setImageByUrl(R.id.id_dir_item_image,item.getFirstImagePath());
				holder.setText(R.id.id_dir_item_count, item.getCount() + "张");
			}
		});
	}

	public interface OnImageDirSelected{
		void selected(ImageFolder folder);
	}

	private OnImageDirSelected mImageDirSelected;

	public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected){
		this.mImageDirSelected=mImageDirSelected;
	}

	@Override
	protected void initEvents() {
		mListDir.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if (mImageDirSelected != null){
					mImageDirSelected.selected(mList.get(position));
				}
			}
		});
	}



}
