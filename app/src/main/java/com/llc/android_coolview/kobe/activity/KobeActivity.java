package com.llc.android_coolview.kobe.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.fragment.SportsFragment;
import com.llc.android_coolview.kobe.fragment.WeatherFragment;
import com.llc.android_coolview.kobe.fragment.TicketsFramgent;
import com.llc.android_coolview.kobe.fragment.NewsFragment;
import com.llc.android_coolview.kobe.impl.MenuOnClickListener;
import com.llc.android_coolview.kobe.impl.TicketsMenuShowListener;
import com.llc.android_coolview.kobe.impl.NewsMenuOnClickListener;
import com.llc.android_coolview.kobe.impl.SportMenuOnClickListener;
import com.llc.android_coolview.kobe.impl.WeatherMenuOnClickListener;
import com.llc.android_coolview.kobe.view.imageview.ChageColorIconView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class KobeActivity extends AppCompatActivity implements OnPageChangeListener, OnClickListener{

	private ViewPager viewPager;
	private ChageColorIconView one,two,three,four;
	private List<Fragment> fragmentList=new ArrayList<Fragment>();
	private List<ChageColorIconView> iconList=new ArrayList<ChageColorIconView>();
	private FragmentPagerAdapter adapter;
	private TicketsMenuShowListener menuShowListener;
	public MenuOnClickListener menuOnClickListener;
	public WeatherMenuOnClickListener weatherMenuOnClickListener;
	public SportMenuOnClickListener sportMenuOnClickListener;
	public NewsMenuOnClickListener newsMenuOnClickListener;
	
	private ShareActionProvider mShareActionProvider;
	private Toolbar mToolbar;
	private static final int[] drawables = { R.drawable.fi, R.drawable.s,R.drawable.bg0_fine_day,R.drawable.t};
	private static String [] titles=null;
	
	private WeatherFragment mWeatherFragment;
	private SportsFragment mSportsFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.setContentView(R.layout.activity_kobe);
		setOverflowShowingAlways();
		initViews();
		initDatas();
		setOnClickListener();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		if(intent!=null){
			String tag=intent.getStringExtra("TagID");
			if(tag!=null){
				String value=intent.getStringExtra("value");
				mWeatherFragment.setCityValue(value);
				viewPager.setCurrentItem(2,false);
			}
		}
	}
	
	private void initViews(){
		titles=new String[]{getResources().getString(R.string.tab_news),getResources().getString(R.string.tab_sports),getResources().getString(R.string.tab_weather),getResources().getString(R.string.tab_ticket)};
		viewPager=(ViewPager) this.findViewById(R.id.viewpager);
		one=(ChageColorIconView) this.findViewById(R.id.id_indicator_one);
		two=(ChageColorIconView) this.findViewById(R.id.id_indicator_two);
		three=(ChageColorIconView) this.findViewById(R.id.id_indicator_three);
		four=(ChageColorIconView) this.findViewById(R.id.id_indicator_four);
		mToolbar=(Toolbar) this.findViewById(R.id.toolbar);
		mToolbar.setTitle(titles[0]);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				KobeActivity.this.finish();
			}
		});
		if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
		colorChange(0);
	}
	
	private void initDatas(){
		iconList.add(one);
		iconList.add(two);
		iconList.add(three);
		iconList.add(four);
		one.setIconAlpha(1.0f);
		initFragments();
		adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return fragmentList.size();
			}
			
			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {
				return fragmentList.get(arg0);
			}
			
		};
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0,false);
	}
	
	private void initFragments(){
		NewsFragment mNewsFragment=new NewsFragment();
		mNewsFragment.mKobeActivity=this;
		fragmentList.add(mNewsFragment);
		
		mSportsFragment=new SportsFragment();
		mSportsFragment.mKobeActivity=this;
		fragmentList.add(mSportsFragment);
		
		mWeatherFragment=new WeatherFragment();
		mWeatherFragment.mKobeActivity=this;
		fragmentList.add(mWeatherFragment);

		TicketsFramgent mTicketsFramgent=new TicketsFramgent();
		mTicketsFramgent.mKobeActivity=this;
		fragmentList.add(mTicketsFramgent);
	}
	
	private void setOnClickListener(){
		viewPager.setOnPageChangeListener(this);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int position, float arg1, int arg2) {
		if (arg1>0) {
			ChageColorIconView left=iconList.get(position);
			ChageColorIconView right=iconList.get(position+1);
			left.setIconAlpha(1-arg1);
			right.setIconAlpha(arg1);
		}
	}

	@Override
	public void onPageSelected(int position) {
		colorChange(position);
		mToolbar.setTitle(titles[position]);
		menuShowListener.showMenu(position);
	}
	
	private void colorChange(int position){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawables[position]);
		Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
			@Override
			public void onGenerated(Palette palette) {
				Palette.Swatch vibrant = palette.getVibrantSwatch();
				mToolbar.setBackgroundColor(vibrant.getRgb());
				//mainBottomMenu.setBackgroundColor(vibrant.getRgb());
				if (android.os.Build.VERSION.SDK_INT >= 21) {
					Window window = getWindow();
					window.setStatusBarColor(colorBurn(vibrant.getRgb()));
					window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
				}
			}
		});
	}
	
	private int colorBurn(int RGBValues) {
		int red = RGBValues >> 16 & 0xFF;
		int green = RGBValues >> 8 & 0xFF;
		int blue = RGBValues & 0xFF;
		red = (int) Math.floor(red * (1 - 0.1));
		green = (int) Math.floor(green * (1 - 0.1));
		blue = (int) Math.floor(blue * (1 - 0.1));
		return Color.rgb(red, green, blue);
	}
	
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		menu.clear();
		getMenuInflater().inflate(R.menu.kobe_news, menu);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/*");
		mShareActionProvider.setShareIntent(intent);
		setMenuShowListener(new TicketsMenuShowListener() {
			@Override
			public void showMenu(int id) {
				menu.clear();
				switch (id) {
				case 0:
					getMenuInflater().inflate(R.menu.kobe_news, menu);
					mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("text/*");
					mShareActionProvider.setShareIntent(intent);
					break;
				case 1:
					getMenuInflater().inflate(R.menu.kobe_sports, menu);
					mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
					Intent kobe_sports = new Intent(Intent.ACTION_SEND);
					kobe_sports.setType("text/*");
					mShareActionProvider.setShareIntent(kobe_sports);
					break;
				case 2:
					getMenuInflater().inflate(R.menu.kobe_weather, menu);
					mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
					Intent kobe_weather = new Intent(Intent.ACTION_SEND);
					kobe_weather.setType("text/*");
					mShareActionProvider.setShareIntent(kobe_weather);
					break;
				case 3:
					getMenuInflater().inflate(R.menu.kobe_ticket, menu);
					mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
					Intent kobe_ticket = new Intent(Intent.ACTION_SEND);
					kobe_ticket.setType("text/*");
					mShareActionProvider.setShareIntent(kobe_ticket);
					break;
				default:
					break;
				}
			}
		});
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.tencent_news:
			newsMenuOnClickListener.onNewsMenuClick(R.id.tencent_news);
			break;
		case R.id.netease_news:
			newsMenuOnClickListener.onNewsMenuClick(R.id.netease_news);
			break;
		case R.id.sina_news:
			newsMenuOnClickListener.onNewsMenuClick(R.id.sina_news);
			break;
			
		case R.id.tencent_sports:
			sportMenuOnClickListener.onSportMenuClick(R.id.tencent_sports);
			break;
		case R.id.netease_sports:
			sportMenuOnClickListener.onSportMenuClick(R.id.netease_sports);
			break;
		case R.id.sina_sports:
			sportMenuOnClickListener.onSportMenuClick(R.id.sina_sports);
			break;
			
		case R.id.weather_location:
			weatherMenuOnClickListener.onWeatherMenuClick(R.id.weather_location);
			break;
		case R.id.weather_city:
			weatherMenuOnClickListener.onWeatherMenuClick(R.id.weather_city);
			break;
		case R.id.weather_city_detail:
			weatherMenuOnClickListener.onWeatherMenuClick(R.id.weather_city_detail);
			break;
			
		case R.id.the_train_number_query_this_train_schedule:
			menuOnClickListener.onMenuClick(R.id.the_train_number_query_this_train_schedule);
			break;
		case R.id.the_station_and_arrival_station_query_train_schedules:
			menuOnClickListener.onMenuClick(R.id.the_station_and_arrival_station_query_train_schedules);
			break;
		case R.id.the_train_number_query_train_through_the_detail_at_the_station:
			menuOnClickListener.onMenuClick(R.id.the_train_number_query_train_through_the_detail_at_the_station);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways() {
		try {
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		resetIconAlpha();
		switch (v.getId()) {
		case R.id.id_indicator_one:
			iconList.get(0).setIconAlpha(1.0f);
			viewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			iconList.get(1).setIconAlpha(1.0f);
			viewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			iconList.get(2).setIconAlpha(1.0f);
			viewPager.setCurrentItem(2,false);
			break;
		case R.id.id_indicator_four:
			iconList.get(3).setIconAlpha(1.0f);
			viewPager.setCurrentItem(3, false);
			break;
		default:
			break;
		}
		menuShowListener.showMenu(viewPager.getCurrentItem());
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		onKobeResumeWindowFocusChanged.resumeWindowFoucsChanged();
		onKobeSportsResumeWindowFocusChanged.sportsResumeWindowFocusChanged();
	}

	private OnKobeResumeWindowFocusChanged onKobeResumeWindowFocusChanged;

	public void setOnKobeResumeWindowFocusChanged(OnKobeResumeWindowFocusChanged onKobeResumeWindowFocusChanged){
		this.onKobeResumeWindowFocusChanged=onKobeResumeWindowFocusChanged;
	}

	public interface OnKobeResumeWindowFocusChanged{
		public void resumeWindowFoucsChanged();
	}

	private OnKobeSportsResumeWindowFocusChanged onKobeSportsResumeWindowFocusChanged;

	public void setOnKobeSportsResumeWindowFocusChanged(OnKobeSportsResumeWindowFocusChanged onKobeSportsResumeWindowFocusChanged){
		this.onKobeSportsResumeWindowFocusChanged=onKobeSportsResumeWindowFocusChanged;
	}

	public interface OnKobeSportsResumeWindowFocusChanged{
		public void sportsResumeWindowFocusChanged();
	}

	private void resetIconAlpha(){
		for(int i=0;i<iconList.size();i++){
			iconList.get(i).setIconAlpha(0);
		}
	}
	
	private void setMenuShowListener(TicketsMenuShowListener menuShowListener){
		this.menuShowListener=menuShowListener;
	}
}
