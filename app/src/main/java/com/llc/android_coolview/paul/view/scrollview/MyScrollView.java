package com.llc.android_coolview.paul.view.scrollview;

import java.util.ArrayList;
import java.util.List;
import com.llc.android_coolview.R;
import com.llc.android_coolview.paul.bean.ImageNames;
import com.llc.android_coolview.paul.view.activity.NBAGamesSpreadActivity;
import com.llc.android_coolview.paul.view.activity.PaulActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.view.View.OnTouchListener;

public class MyScrollView extends ScrollView implements OnTouchListener {

	/**
	 * 每页要加载的图片数量
	 */
	public static final int PAGE_SIZE = 15;

	/**
	 * 记录当前已加载到第几页
	 */
	private int page;

	/**
	 * 每一列的宽度
	 */
	private int columnWidth;

	/**
	 * 当前第一列的高度
	 */
	private int firstColumnHeight;

	/**
	 * 当前第二列的高度
	 */
	private int secondColumnHeight;

	/**
	 * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
	 */
	private boolean loadOnce;

	/**
	 * 第一列的布局
	 */
	private LinearLayout firstColumn;

	/**
	 * 第二列的布局
	 */
	private LinearLayout secondColumn;

	/**
	 * MyScrollView下的直接子布局。
	 */
	private static View scrollLayout;

	/**
	 * MyScrollView布局的高度。
	 */
	private static int scrollViewHeight;

	/**
	 * 记录上垂直方向的滚动距离。
	 */
	private static int lastScrollY = -1;

	/**
	 * 记录所有界面上的图片，用以可以随时控制对图片的释放。
	 */
	private List<ImageView> imageViewList = new ArrayList<ImageView>();

	/**
	 * 记录所有界面的文字
	 * */
	private ImageView mImageView;
	/**
	 * 在Handler中进行图片可见性检查的判断，以及加载更多图片的操作。
	 */
	private static Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			MyScrollView myScrollView = (MyScrollView) msg.obj;
			int scrollY = myScrollView.getScrollY();
			// 如果当前的滚动位置和上次相同，表示已停止滚动
			if (scrollY == lastScrollY) {
				// 当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
				if (scrollViewHeight + scrollY >= scrollLayout.getHeight()) {
					myScrollView.loadMoreImages();
				}
			} else {
				lastScrollY = scrollY;
				Message message = new Message();
				message.obj = myScrollView;
				// 5毫秒后再次对滚动位置进行判断
				handler.sendMessageDelayed(message, 5);
			}
		};

	};

	/**
	 * MyScrollView的构造函数。
	 * 
	 * @param context
	 * @param attrs
	 */
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(this);
	}

	/**
	 * 进行一些关键性的初始化操作，获取MyScrollView的高度，以及得到第一列的宽度值。并在这里开始加载第一页的图片。
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			scrollViewHeight = getHeight();
			scrollLayout = getChildAt(0);
			firstColumn = (LinearLayout) findViewById(R.id.first_column);
			secondColumn = (LinearLayout) findViewById(R.id.second_column);
			columnWidth = firstColumn.getWidth();
			loadOnce = true;
			loadMoreImages();
		}
	}

	/**
	 * 监听用户的触屏事件，如果用户手指离开屏幕则开始进行滚动检测。
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			Message message = new Message();
			message.obj = this;
			handler.sendMessageDelayed(message, 5);
		}
		return false;
	}

	/**
	 * 开始加载下一页的图片，每张图片都会开启一个异步线程去下载。
	 */
	public void loadMoreImages() {
		int startIndex = page * PAGE_SIZE;
		int endIndex = page * PAGE_SIZE + PAGE_SIZE;
		if (startIndex < ImageNames.images.length) {
			if (endIndex > ImageNames.images.length) {
				endIndex = ImageNames.images.length;
			}
			for (int i = startIndex; i < endIndex; i++) {
				Bitmap bitmap=PaulActivity.images.get(i);
				if (bitmap != null) {
					double ratio = bitmap.getWidth() / (columnWidth * 1.0);
					int scaledHeight = (int) (bitmap.getHeight() / ratio);
					addImage(bitmap, columnWidth, scaledHeight,i);
				}
			}
			page++;
		} else {
			Toast.makeText(getContext(), "所有球队展示结束", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 向ImageView中添加一张图片
	 * 
	 * @param bitmap
	 *            待添加的图片
	 * @param imageWidth
	 *            图片的宽度
	 * @param imageHeight
	 *            图片的高度
	 */
	private void addImage(Bitmap bitmap, int imageWidth, int imageHeight,int i) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth, imageHeight);
		if (mImageView != null) {
			mImageView.setImageBitmap(bitmap);
		} else {
			ImageView imageView = new ImageView(getContext());
			imageView.setLayoutParams(params);
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setPadding(5, 5, 5, 5);
			imageView.setTag(i);
			findColumnToAdd(imageView, imageHeight).addView(imageView);
			imageViewList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(final View v) {
					Toast.makeText(getContext(), PaulActivity.teamName.get((Integer) v.getTag()), Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(getContext(),NBAGamesSpreadActivity.class);
					intent.putExtra("teamName", PaulActivity.teamName.get((Integer) v.getTag()));
					getContext().startActivity(intent);
				}
			});
		}
	}

	/**
	 * 找到此时应该添加图片的一列。原则就是对两列的高度进行判断，当前高度最小的一列就是应该添加的一列。
	 * 
	 * @param imageView
	 * @param imageHeight
	 * @return 应该添加图片的一列
	 */
	private LinearLayout findColumnToAdd(ImageView imageView,int imageHeight) {
		if (firstColumnHeight <= secondColumnHeight) {
			imageView.setTag(R.string.border_top, firstColumnHeight);
			firstColumnHeight += imageHeight;
			imageView.setTag(R.string.border_bottom, firstColumnHeight);
			return firstColumn;
		} else {
			imageView.setTag(R.string.border_top, secondColumnHeight);
			secondColumnHeight += imageHeight;
			imageView.setTag(R.string.border_bottom, secondColumnHeight);
			return secondColumn;
		}
	}
}
